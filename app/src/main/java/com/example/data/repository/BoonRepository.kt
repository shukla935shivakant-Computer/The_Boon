package com.example.data.repository

import com.example.audio.AudioSynthesizer
import com.example.data.local.AppDatabase
import com.example.data.local.BadgeEntity
import com.example.data.local.CertificateEntity
import com.example.data.local.CreatorQuestEntity
import com.example.data.local.ProofSubmissionEntity
import com.example.data.local.QuestEntity
import com.example.data.local.UserProfileEntity
import com.example.data.seed.BadgesSeed
import com.example.data.seed.QuestsSeed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class BoonRepository(private val db: AppDatabase) {
    val userProfile: Flow<UserProfileEntity?> = db.userDao().getUserProfile()
    val allQuests: Flow<List<QuestEntity>> = db.questDao().getAllQuests()
    val allSubmissions: Flow<List<ProofSubmissionEntity>> = db.proofDao().getAllSubmissions()
    val allBadges: Flow<List<BadgeEntity>> = db.badgeDao().getAllBadges()
    val allCertificates: Flow<List<CertificateEntity>> = db.certificateDao().getAllCertificates()
    val allCreatorQuests: Flow<List<CreatorQuestEntity>> = db.creatorDao().getAllCreatorQuests()

    suspend fun initializeIfEmpty() {
        val user = db.userDao().getUserProfileOnce()
        if (user == null) {
            // Clean Zero-Data Standard: XP = 0, Level = 1, Streak = 0, isOnboarded = false
            db.userDao().insertOrUpdate(
                UserProfileEntity(
                    id = 1,
                    name = "Cosmo Explorer",
                    avatarKey = "Fox",
                    xp = 0,
                    level = 1,
                    streakDays = 0,
                    isOnboarded = false
                )
            )
        }

        val existingQuests = db.questDao().getAllQuests().firstOrNull() ?: emptyList()
        if (existingQuests.isEmpty()) {
            db.questDao().insertQuests(QuestsSeed.getDefaultQuests())
        }

        val existingBadges = db.badgeDao().getAllBadges().firstOrNull() ?: emptyList()
        if (existingBadges.isEmpty()) {
            db.badgeDao().insertBadges(BadgesSeed.getDefaultBadges())
        }
    }

    suspend fun completeOnboarding(name: String, avatarKey: String, photoUri: String?) {
        val current = db.userDao().getUserProfileOnce() ?: UserProfileEntity()
        db.userDao().insertOrUpdate(
            current.copy(
                name = name.ifBlank { "Cosmo Explorer" },
                avatarKey = avatarKey,
                customPhotoUri = photoUri,
                isOnboarded = true
            )
        )
    }

    suspend fun submitQuestProof(
        questId: String,
        questTitle: String,
        category: String,
        xpReward: Int,
        photoUri: String?,
        audioUri: String?,
        notes: String
    ) {
        // 1. Mark quest completed in DB
        db.questDao().markQuestCompleted(questId)

        // 2. Add proof submission
        db.proofDao().insertSubmission(
            ProofSubmissionEntity(
                questId = questId,
                questTitle = questTitle,
                category = category,
                xpEarned = xpReward,
                photoUri = photoUri,
                audioUri = audioUri,
                notes = notes
            )
        )

        // 3. Update User XP, Level, and Streak
        val user = db.userDao().getUserProfileOnce() ?: UserProfileEntity()
        val newXp = user.xp + xpReward
        val newLevel = calculateLevel(newXp)
        val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

        val newStreak = if (user.lastActiveDate == todayStr) {
            user.streakDays
        } else {
            user.streakDays + 1
        }

        db.userDao().insertOrUpdate(
            user.copy(
                xp = newXp,
                level = newLevel,
                streakDays = newStreak,
                lastActiveDate = todayStr
            )
        )

        // 4. Check & Unlock Badges
        checkBadgesMilestones(category, newStreak)

        // 5. Sound fanfare!
        AudioSynthesizer.playFanfare()
    }

    suspend fun awardManualXp(amount: Int, category: String, reason: String) {
        val user = db.userDao().getUserProfileOnce() ?: UserProfileEntity()
        val newXp = user.xp + amount
        val newLevel = calculateLevel(newXp)

        db.userDao().insertOrUpdate(
            user.copy(
                xp = newXp,
                level = newLevel
            )
        )
        AudioSynthesizer.playCorrect()

        // Check if level increased
        if (newLevel > user.level) {
            AudioSynthesizer.playFanfare()
        }
    }

    private suspend fun checkBadgesMilestones(category: String, streak: Int) {
        val now = System.currentTimeMillis()
        // Badge 1: First Step
        db.badgeDao().unlockBadge("first_step", now)

        // Check Nature count
        val allSubs = db.proofDao().getAllSubmissions().firstOrNull() ?: emptyList()
        val natureCount = allSubs.count { it.category.equals("Nature", ignoreCase = true) }
        if (natureCount >= 3) {
            db.badgeDao().unlockBadge("nature_scout", now)
        }

        // Science badge
        if (category.equals("Science", ignoreCase = true)) {
            db.badgeDao().unlockBadge("science_whiz", now)
        }

        // Kindness badge
        if (category.equals("Gratitude", ignoreCase = true) || category.equals("Social", ignoreCase = true)) {
            db.badgeDao().unlockBadge("kindness_hero", now)
        }

        // Streak badge
        if (streak >= 1) {
            db.badgeDao().unlockBadge("streak_fire", now)
        }

        // Generate Certificates if milestone achieved!
        checkCertificateAwards(allSubs.size + 1, natureCount, streak)
    }

    private suspend fun checkCertificateAwards(totalCompleted: Int, natureCount: Int, streak: Int) {
        val user = db.userDao().getUserProfileOnce() ?: return
        val dateStr = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault()).format(Date())

        // Check if First Real-World Hero cert exists
        val certs = db.certificateDao().getAllCertificates().firstOrNull() ?: emptyList()
        if (totalCompleted >= 1 && certs.none { it.criteria.contains("First Real-World Quest") }) {
            db.certificateDao().insertCertificate(
                CertificateEntity(
                    certificateNo = "BOON-Q1-${(1000..9999).random()}",
                    title = "Certified Junior Explorer",
                    criteria = "Successfully completed their First Real-World Quest and logged offline proof.",
                    recipientName = user.name,
                    dateEarned = dateStr
                )
            )
        }

        if (natureCount >= 3 && certs.none { it.criteria.contains("Nature") }) {
            db.certificateDao().insertCertificate(
                CertificateEntity(
                    certificateNo = "BOON-NAT-${(1000..9999).random()}",
                    title = "Official Forest Ranger Certificate",
                    criteria = "Mastery in 3 botanical, weather, and wildlife observational field quests.",
                    recipientName = user.name,
                    dateEarned = dateStr
                )
            )
        }

        if (streak >= 7 && certs.none { it.criteria.contains("7-Day") }) {
            db.certificateDao().insertCertificate(
                CertificateEntity(
                    certificateNo = "BOON-STR-${(1000..9999).random()}",
                    title = "Grand Habit Champion Certificate",
                    criteria = "Outstanding commitment to daily offline curiosity and real-world growth.",
                    recipientName = user.name,
                    dateEarned = dateStr
                )
            )
        }
    }

    suspend fun createCustomQuest(title: String, category: String, instructions: String, xp: Int, proofType: String) {
        db.creatorDao().insertCreatorQuest(
            CreatorQuestEntity(
                title = title,
                category = category,
                instructions = instructions,
                xpReward = xp,
                proofType = proofType
            )
        )
        // Also insert into main quest list so hero can complete it!
        db.questDao().insertQuests(
            listOf(
                QuestEntity(
                    id = "custom_${System.currentTimeMillis()}",
                    category = category,
                    titleEn = title,
                    descriptionEn = instructions,
                    titleHi = title,
                    descriptionHi = instructions,
                    titleEs = title,
                    descriptionEs = instructions,
                    titleFr = title,
                    descriptionFr = instructions,
                    titleZh = title,
                    descriptionZh = instructions,
                    xpReward = xp,
                    difficulty = "Medium",
                    estimatedMinutes = 15,
                    isCustom = true
                )
            )
        )
        // Unlock creator star badge
        db.badgeDao().unlockBadge("creator_star", System.currentTimeMillis())
        AudioSynthesizer.playCorrect()
    }

    suspend fun likeCreatorQuest(id: Long) {
        db.creatorDao().incrementLike(id)
        AudioSynthesizer.playPop()
    }

    suspend fun bookmarkCreatorQuest(id: Long) {
        db.creatorDao().toggleBookmark(id)
        AudioSynthesizer.playPop()
    }

    suspend fun updateLanguage(lang: String) {
        db.userDao().updateLanguage(lang)
    }

    suspend fun updateAudioSettings(sfx: Float, music: Float, musicOn: Boolean) {
        db.userDao().updateAudioSettings(sfx, music, musicOn)
        AudioSynthesizer.sfxVolume = sfx
        AudioSynthesizer.musicVolume = music
        AudioSynthesizer.isMusicEnabled = musicOn
    }

    suspend fun resetAllDataToZero() {
        // Clean wipe of all user data, proofs, custom quests, certificates, reset badges & quests
        db.proofDao().clearSubmissions()
        db.certificateDao().clearCertificates()
        db.creatorDao().clearCreatorQuests()
        db.questDao().resetAllQuests()
        db.questDao().deleteCustomQuests()
        db.badgeDao().resetBadges()

        db.userDao().clearUser()
        db.userDao().insertOrUpdate(
            UserProfileEntity(
                id = 1,
                name = "Cosmo Explorer",
                avatarKey = "Fox",
                xp = 0,
                level = 1,
                streakDays = 0,
                isOnboarded = false
            )
        )
        AudioSynthesizer.playPop()
    }

    companion object {
        fun calculateLevel(xp: Int): Int {
            return when {
                xp < 100 -> 1 // Sprout Explorer (0 - 99 XP)
                xp < 300 -> 2 // Curious Seedling (100 - 299 XP)
                xp < 600 -> 3 // Branch Builder (300 - 599 XP)
                xp < 1000 -> 4 // Nature Guardian (600 - 999 XP)
                else -> 5 + ((xp - 1000) / 500) // Master Innovator
            }
        }

        fun getLevelTitle(level: Int, lang: String = "en"): String {
            val key = when (level) {
                1 -> "lvl_1_name"
                2 -> "lvl_2_name"
                3 -> "lvl_3_name"
                4 -> "lvl_4_name"
                else -> "lvl_5_name"
            }
            return com.example.data.i18n.BoonStrings.get(key, lang)
        }

        fun getCertificateTitle(cert: CertificateEntity, lang: String): String {
            return getCertificateTitle(cert.title, lang)
        }

        fun getCertificateTitle(title: String, lang: String): String {
            return when {
                title.contains("Junior", ignoreCase = true) || title.contains("First Real-World", ignoreCase = true) -> com.example.data.i18n.BoonStrings.get("cert_title_explorer", lang)
                title.contains("Forest", ignoreCase = true) || title.contains("Nature", ignoreCase = true) -> com.example.data.i18n.BoonStrings.get("cert_title_ranger", lang)
                else -> com.example.data.i18n.BoonStrings.get("cert_title_habit", lang)
            }
        }

        fun getCertificateCriteria(cert: CertificateEntity, lang: String): String {
            return getCertificateCriteria(cert.criteria, lang)
        }

        fun getCertificateCriteria(criteria: String, lang: String): String {
            return when {
                criteria.contains("Junior", ignoreCase = true) || criteria.contains("First Real-World", ignoreCase = true) -> com.example.data.i18n.BoonStrings.get("cert_crit_explorer", lang)
                criteria.contains("Forest", ignoreCase = true) || criteria.contains("Nature", ignoreCase = true) -> com.example.data.i18n.BoonStrings.get("cert_crit_ranger", lang)
                else -> com.example.data.i18n.BoonStrings.get("cert_crit_habit", lang)
            }
        }

        fun getNextLevelTargetXp(level: Int): Int {
            return when (level) {
                1 -> 100
                2 -> 300
                3 -> 600
                4 -> 1000
                else -> 1000 + (level - 4) * 500
            }
        }
    }
}
