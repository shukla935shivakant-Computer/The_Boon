package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val name: String = "Cosmo Explorer",
    val avatarKey: String = "Fox",
    val customPhotoUri: String? = null,
    val xp: Int = 0,
    val level: Int = 1,
    val streakDays: Int = 0,
    val lastActiveDate: String = "",
    val languageCode: String = "en",
    val sfxVolume: Float = 0.8f,
    val musicVolume: Float = 0.4f,
    val isMusicEnabled: Boolean = false,
    val isOnboarded: Boolean = false
)

@Entity(tableName = "quests")
data class QuestEntity(
    @PrimaryKey val id: String,
    val category: String,
    val titleEn: String,
    val descriptionEn: String,
    val titleHi: String,
    val descriptionHi: String,
    val titleEs: String,
    val descriptionEs: String,
    val titleFr: String,
    val descriptionFr: String,
    val titleZh: String,
    val descriptionZh: String,
    val xpReward: Int,
    val difficulty: String, // Easy, Medium, Epic
    val estimatedMinutes: Int,
    val isCompleted: Boolean = false,
    val isCustom: Boolean = false
) {
    fun getTitle(lang: String): String = when (lang.lowercase()) {
        "hi" -> titleHi.ifBlank { titleEn }
        "es" -> titleEs.ifBlank { titleEn }
        "fr" -> titleFr.ifBlank { titleEn }
        "zh" -> titleZh.ifBlank { titleEn }
        else -> titleEn
    }

    fun getDescription(lang: String): String = when (lang.lowercase()) {
        "hi" -> descriptionHi.ifBlank { descriptionEn }
        "es" -> descriptionEs.ifBlank { descriptionEn }
        "fr" -> descriptionFr.ifBlank { descriptionEn }
        "zh" -> descriptionZh.ifBlank { descriptionEn }
        else -> descriptionEn
    }
}

@Entity(tableName = "proof_submissions")
data class ProofSubmissionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val questId: String,
    val questTitle: String,
    val category: String,
    val xpEarned: Int,
    val timestamp: Long = System.currentTimeMillis(),
    val photoUri: String? = null,
    val audioUri: String? = null,
    val notes: String = ""
)

@Entity(tableName = "badges")
data class BadgeEntity(
    @PrimaryKey val id: String,
    val titleEn: String,
    val descEn: String,
    val titleHi: String,
    val descHi: String,
    val titleEs: String,
    val descEs: String,
    val titleFr: String,
    val descFr: String,
    val titleZh: String,
    val descZh: String,
    val iconEmoji: String,
    val isUnlocked: Boolean = false,
    val unlockedAt: Long? = null
) {
    fun getTitle(lang: String): String = when (lang.lowercase()) {
        "hi" -> titleHi.ifBlank { titleEn }
        "es" -> titleEs.ifBlank { titleEn }
        "fr" -> titleFr.ifBlank { titleEn }
        "zh" -> titleZh.ifBlank { titleEn }
        else -> titleEn
    }

    fun getDesc(lang: String): String = when (lang.lowercase()) {
        "hi" -> descHi.ifBlank { descEn }
        "es" -> descEs.ifBlank { descEn }
        "fr" -> descFr.ifBlank { descEn }
        "zh" -> descZh.ifBlank { descEn }
        else -> descEn
    }
}

@Entity(tableName = "certificates")
data class CertificateEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val certificateNo: String,
    val title: String,
    val criteria: String,
    val recipientName: String,
    val dateEarned: String
)

@Entity(tableName = "creator_quests")
data class CreatorQuestEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val category: String,
    val instructions: String,
    val xpReward: Int,
    val proofType: String,
    val viewsCount: Int = 1,
    val likesCount: Int = 0,
    val isBookmarked: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
