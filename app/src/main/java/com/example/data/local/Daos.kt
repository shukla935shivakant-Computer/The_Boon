package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    fun getUserProfile(): Flow<UserProfileEntity?>

    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    suspend fun getUserProfileOnce(): UserProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(profile: UserProfileEntity)

    @Query("UPDATE user_profile SET xp = xp + :additionalXp, level = :newLevel WHERE id = 1")
    suspend fun addXp(additionalXp: Int, newLevel: Int)

    @Query("UPDATE user_profile SET streakDays = :streak, lastActiveDate = :date WHERE id = 1")
    suspend fun updateStreak(streak: Int, date: String)

    @Query("UPDATE user_profile SET languageCode = :lang WHERE id = 1")
    suspend fun updateLanguage(lang: String)

    @Query("UPDATE user_profile SET sfxVolume = :sfx, musicVolume = :music, isMusicEnabled = :musicOn WHERE id = 1")
    suspend fun updateAudioSettings(sfx: Float, music: Float, musicOn: Boolean)

    @Query("DELETE FROM user_profile")
    suspend fun clearUser()
}

@Dao
interface QuestDao {
    @Query("SELECT * FROM quests ORDER BY isCompleted ASC, xpReward ASC")
    fun getAllQuests(): Flow<List<QuestEntity>>

    @Query("SELECT * FROM quests WHERE category = :cat ORDER BY isCompleted ASC")
    fun getQuestsByCategory(cat: String): Flow<List<QuestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuests(quests: List<QuestEntity>)

    @Query("UPDATE quests SET isCompleted = 1 WHERE id = :id")
    suspend fun markQuestCompleted(id: String)

    @Query("UPDATE quests SET isCompleted = 0")
    suspend fun resetAllQuests()

    @Query("DELETE FROM quests WHERE isCustom = 1")
    suspend fun deleteCustomQuests()
}

@Dao
interface ProofDao {
    @Query("SELECT * FROM proof_submissions ORDER BY timestamp DESC")
    fun getAllSubmissions(): Flow<List<ProofSubmissionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubmission(submission: ProofSubmissionEntity): Long

    @Query("DELETE FROM proof_submissions")
    suspend fun clearSubmissions()
}

@Dao
interface BadgeDao {
    @Query("SELECT * FROM badges")
    fun getAllBadges(): Flow<List<BadgeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBadges(badges: List<BadgeEntity>)

    @Query("UPDATE badges SET isUnlocked = 1, unlockedAt = :time WHERE id = :id AND isUnlocked = 0")
    suspend fun unlockBadge(id: String, time: Long)

    @Query("UPDATE badges SET isUnlocked = 0, unlockedAt = NULL")
    suspend fun resetBadges()
}

@Dao
interface CertificateDao {
    @Query("SELECT * FROM certificates ORDER BY id DESC")
    fun getAllCertificates(): Flow<List<CertificateEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCertificate(cert: CertificateEntity): Long

    @Query("DELETE FROM certificates")
    suspend fun clearCertificates()
}

@Dao
interface CreatorDao {
    @Query("SELECT * FROM creator_quests ORDER BY createdAt DESC")
    fun getAllCreatorQuests(): Flow<List<CreatorQuestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCreatorQuest(quest: CreatorQuestEntity): Long

    @Query("UPDATE creator_quests SET likesCount = likesCount + 1 WHERE id = :id")
    suspend fun incrementLike(id: Long)

    @Query("UPDATE creator_quests SET isBookmarked = NOT isBookmarked WHERE id = :id")
    suspend fun toggleBookmark(id: Long)

    @Query("DELETE FROM creator_quests")
    suspend fun clearCreatorQuests()
}
