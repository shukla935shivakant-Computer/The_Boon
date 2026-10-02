package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai.BoonBotService
import com.example.audio.AudioSynthesizer
import com.example.audio.BoonTtsHelper
import com.example.data.i18n.BoonStrings
import com.example.data.local.AppDatabase
import com.example.data.local.BadgeEntity
import com.example.data.local.CertificateEntity
import com.example.data.local.CreatorQuestEntity
import com.example.data.local.ProofSubmissionEntity
import com.example.data.local.QuestEntity
import com.example.data.local.UserProfileEntity
import com.example.data.repository.BoonRepository
import com.example.data.seed.RiddleItem
import com.example.data.seed.RiddlesSeed
import com.example.data.seed.ScienceExperiment
import com.example.data.seed.ScienceExperimentsSeed
import com.example.data.seed.StoriesSeed
import com.example.data.seed.StoryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class BotMessage(
    val isUser: Boolean,
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

class BoonViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val repo = BoonRepository(db)
    val tts = BoonTtsHelper(application)

    // User Profile
    val userProfile: StateFlow<UserProfileEntity?> = repo.userProfile.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    // Quests
    val allQuests: StateFlow<List<QuestEntity>> = repo.allQuests.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Proof Submissions
    val allSubmissions: StateFlow<List<ProofSubmissionEntity>> = repo.allSubmissions.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Badges
    val allBadges: StateFlow<List<BadgeEntity>> = repo.allBadges.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Certificates
    val allCertificates: StateFlow<List<CertificateEntity>> = repo.allCertificates.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Creator Quests
    val allCreatorQuests: StateFlow<List<CreatorQuestEntity>> = repo.allCreatorQuests.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Active Tab (0: Quests, 1: Science, 2: Games, 3: Riddles, 4: Stories, 5: Creator, 6: Profile)
    private val _currentTab = MutableStateFlow(0)
    val currentTab: StateFlow<Int> = _currentTab.asStateFlow()

    // Quest filter
    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    // Active Quest for Proof Submission Dialog
    private val _activeProofQuest = MutableStateFlow<QuestEntity?>(null)
    val activeProofQuest: StateFlow<QuestEntity?> = _activeProofQuest.asStateFlow()

    // Science Experiments
    val scienceExperiments = ScienceExperimentsSeed.getExperiments()
    private val _activeExperiment = MutableStateFlow<ScienceExperiment?>(null)
    val activeExperiment: StateFlow<ScienceExperiment?> = _activeExperiment.asStateFlow()

    // Riddles
    val riddlesList = RiddlesSeed.getRiddles()
    private val _currentRiddleIndex = MutableStateFlow(0)
    val currentRiddleIndex: StateFlow<Int> = _currentRiddleIndex.asStateFlow()
    private val _riddleOptions = MutableStateFlow<List<String>>(emptyList())
    val riddleOptions: StateFlow<List<String>> = _riddleOptions.asStateFlow()
    private val _riddleSelectedOption = MutableStateFlow<String?>(null)
    val riddleSelectedOption: StateFlow<String?> = _riddleSelectedOption.asStateFlow()
    private val _isRiddleCorrect = MutableStateFlow<Boolean?>(null)
    val isRiddleCorrect: StateFlow<Boolean?> = _isRiddleCorrect.asStateFlow()
    private val _isHintRevealed = MutableStateFlow(false)
    val isHintRevealed: StateFlow<Boolean> = _isHintRevealed.asStateFlow()
    private val _solvedRiddlesCount = MutableStateFlow(0)
    val solvedRiddlesCount: StateFlow<Int> = _solvedRiddlesCount.asStateFlow()

    // Stories
    private val _storiesList = MutableStateFlow<List<StoryItem>>(StoriesSeed.getStories())
    val storiesList: StateFlow<List<StoryItem>> = _storiesList.asStateFlow()
    private val _activeStory = MutableStateFlow<StoryItem?>(null)
    val activeStory: StateFlow<StoryItem?> = _activeStory.asStateFlow()
    private val _isStoryPlaying = MutableStateFlow(false)
    val isStoryPlaying: StateFlow<Boolean> = _isStoryPlaying.asStateFlow()

    // BoonBot Chat
    private val _isBotDialogOpen = MutableStateFlow(false)
    val isBotDialogOpen: StateFlow<Boolean> = _isBotDialogOpen.asStateFlow()
    private val _botMessages = MutableStateFlow<List<BotMessage>>(
        listOf(
            BotMessage(isUser = false, text = "Hello Hero Explorer! 🌱 I'm BoonBot. Ask me about quests, experiments, riddles, or tips when you feel bored!")
        )
    )
    val botMessages: StateFlow<List<BotMessage>> = _botMessages.asStateFlow()
    private val _isBotThinking = MutableStateFlow(false)
    val isBotThinking: StateFlow<Boolean> = _isBotThinking.asStateFlow()

    // Settings & Dialogs
    private val _isSettingsOpen = MutableStateFlow(false)
    val isSettingsOpen: StateFlow<Boolean> = _isSettingsOpen.asStateFlow()
    private val _celebrationXp = MutableStateFlow<Pair<Int, String>?>(null)
    val celebrationXp: StateFlow<Pair<Int, String>?> = _celebrationXp.asStateFlow()

    init {
        viewModelScope.launch {
            repo.initializeIfEmpty()
            val user = userProfile.value
            val lang = user?.languageCode ?: "en"
            updateRiddleOptions(0, lang)
            _botMessages.value = listOf(BotMessage(isUser = false, text = BoonStrings.get("bot_welcome_msg", lang)))
        }
    }

    fun selectTab(index: Int) {
        _currentTab.value = index
        AudioSynthesizer.playPop()
    }

    fun setCategoryFilter(category: String) {
        _selectedCategory.value = category
        AudioSynthesizer.playPop()
    }

    fun openProofDialog(quest: QuestEntity) {
        _activeProofQuest.value = quest
        AudioSynthesizer.playPop()
    }

    fun closeProofDialog() {
        _activeProofQuest.value = null
    }

    fun submitProof(
        questId: String,
        title: String,
        category: String,
        xp: Int,
        photoUri: String?,
        audioUri: String?,
        notes: String
    ) {
        viewModelScope.launch {
            repo.submitQuestProof(
                questId = questId,
                questTitle = title,
                category = category,
                xpReward = xp,
                photoUri = photoUri,
                audioUri = audioUri,
                notes = notes
            )
            _activeProofQuest.value = null
            _celebrationXp.value = Pair(xp, title)
        }
    }

    fun openExperiment(exp: ScienceExperiment) {
        _activeExperiment.value = exp
        AudioSynthesizer.playPop()
    }

    fun closeExperiment() {
        _activeExperiment.value = null
    }

    fun submitExperimentProof(exp: ScienceExperiment, photoUri: String?, notes: String) {
        viewModelScope.launch {
            val lang = userProfile.value?.languageCode ?: "en"
            val title = exp.getTitle(lang)
            repo.submitQuestProof(
                questId = "exp_${exp.id}",
                questTitle = title,
                category = "Science",
                xpReward = exp.xpReward,
                photoUri = photoUri,
                audioUri = null,
                notes = notes.ifBlank { "Completed science experiment: $title" }
            )
            _activeExperiment.value = null
            _celebrationXp.value = Pair(exp.xpReward, title)
        }
    }

    // Riddle Logic
    fun updateRiddleOptions(index: Int, lang: String = "en") {
        if (index in riddlesList.indices) {
            val riddle = riddlesList[index]
            _riddleOptions.value = riddle.getAllShuffledOptions(lang)
            _riddleSelectedOption.value = null
            _isRiddleCorrect.value = null
            _isHintRevealed.value = false
        }
    }

    fun selectRiddleOption(option: String, lang: String) {
        if (_isRiddleCorrect.value == true) return
        _riddleSelectedOption.value = option
        val riddle = riddlesList[_currentRiddleIndex.value]
        if (option.equals(riddle.getCorrectAnswer(lang), ignoreCase = true)) {
            _isRiddleCorrect.value = true
            _solvedRiddlesCount.value += 1
            AudioSynthesizer.playCorrect()
            viewModelScope.launch {
                repo.awardManualXp(50, "Riddles", "Solved Riddle: ${riddle.getCorrectAnswer(lang)}")
            }
        } else {
            _isRiddleCorrect.value = false
            AudioSynthesizer.playWrong()
        }
    }

    fun nextRiddle(lang: String) {
        val nextIdx = (_currentRiddleIndex.value + 1) % riddlesList.size
        _currentRiddleIndex.value = nextIdx
        updateRiddleOptions(nextIdx, lang)
        AudioSynthesizer.playPop()
    }

    fun revealRiddleHint() {
        _isHintRevealed.value = true
        AudioSynthesizer.playPop()
    }

    fun speakRiddle(languageCode: String) {
        val riddle = riddlesList[_currentRiddleIndex.value]
        val textToSpeak = riddle.getQuestion(languageCode)
        tts.speak(textToSpeak, languageCode)
    }

    // Stories Logic
    fun openStory(story: StoryItem) {
        _activeStory.value = story
        AudioSynthesizer.playPop()
    }

    fun closeStory() {
        stopStoryAudio()
        _activeStory.value = null
    }

    fun toggleStoryAudio(story: StoryItem, languageCode: String) {
        if (_isStoryPlaying.value) {
            stopStoryAudio()
        } else {
            _isStoryPlaying.value = true
            tts.speak(story.getContent(languageCode), languageCode)
        }
    }

    fun stopStoryAudio() {
        _isStoryPlaying.value = false
        tts.stop()
    }

    fun cheerStory(storyId: String) {
        _storiesList.value = _storiesList.value.map {
            if (it.id == storyId) it.copy(cheersCount = it.cheersCount + 1) else it
        }
        AudioSynthesizer.playCorrect()
    }

    // Creator Hub
    fun createQuest(title: String, category: String, instructions: String, xp: Int, proofType: String) {
        viewModelScope.launch {
            repo.createCustomQuest(title, category, instructions, xp, proofType)
        }
    }

    fun likeCreatorQuest(id: Long) {
        viewModelScope.launch {
            repo.likeCreatorQuest(id)
        }
    }

    fun bookmarkCreatorQuest(id: Long) {
        viewModelScope.launch {
            repo.bookmarkCreatorQuest(id)
        }
    }

    // BoonBot
    fun openBotDialog() {
        _isBotDialogOpen.value = true
        AudioSynthesizer.playPop()
    }

    fun closeBotDialog() {
        _isBotDialogOpen.value = false
    }

    fun sendBotMessage(text: String, languageCode: String) {
        if (text.isBlank()) return
        val currentList = _botMessages.value.toMutableList()
        currentList.add(BotMessage(isUser = true, text = text))
        _botMessages.value = currentList
        AudioSynthesizer.playPop()

        _isBotThinking.value = true
        viewModelScope.launch {
            val response = BoonBotService.askBoonBot(text, languageCode)
            val updated = _botMessages.value.toMutableList()
            updated.add(BotMessage(isUser = false, text = response))
            _botMessages.value = updated
            _isBotThinking.value = false
            AudioSynthesizer.playCorrect()
        }
    }

    fun speakBotResponse(text: String, languageCode: String) {
        tts.speak(text, languageCode)
    }

    // Onboarding
    fun completeOnboarding(name: String, avatar: String, photoUri: String?) {
        viewModelScope.launch {
            repo.completeOnboarding(name, avatar, photoUri)
            AudioSynthesizer.playFanfare()
        }
    }

    // Settings
    fun toggleSettings(open: Boolean) {
        _isSettingsOpen.value = open
        AudioSynthesizer.playPop()
    }

    fun setLanguage(lang: String) {
        viewModelScope.launch {
            repo.updateLanguage(lang)
            updateRiddleOptions(_currentRiddleIndex.value, lang)
            if (_botMessages.value.size <= 1) {
                _botMessages.value = listOf(BotMessage(isUser = false, text = BoonStrings.get("bot_welcome_msg", lang)))
            }
            AudioSynthesizer.playPop()
        }
    }

    fun setAudioSettings(sfx: Float, music: Float, musicOn: Boolean) {
        viewModelScope.launch {
            repo.updateAudioSettings(sfx, music, musicOn)
        }
    }

    fun resetAppToZero() {
        viewModelScope.launch {
            repo.resetAllDataToZero()
            _celebrationXp.value = null
            _isSettingsOpen.value = false
        }
    }

    fun dismissCelebration() {
        _celebrationXp.value = null
    }

    override fun onCleared() {
        super.onCleared()
        tts.shutdown()
        AudioSynthesizer.stopAmbientMusic()
    }
}
