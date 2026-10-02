package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.audio.AudioSynthesizer
import com.example.ui.components.BoonBotDialog
import com.example.ui.components.BoonNavBar
import com.example.ui.components.BoonTopBar
import com.example.ui.components.XpCelebrationDialog
import com.example.ui.screens.CreatorHubScreen
import com.example.ui.screens.MindGamesScreen
import com.example.ui.screens.OnboardingDialog
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.QuestsScreen
import com.example.ui.screens.RiddlesScreen
import com.example.ui.screens.ScienceLabScreen
import com.example.ui.screens.SettingsDialog
import com.example.ui.screens.StoriesScreen
import com.example.ui.theme.AmberGold
import com.example.ui.theme.BoonTheme
import com.example.ui.theme.EmeraldDark
import com.example.ui.theme.EmeraldPrimary
import com.example.ui.viewmodel.BoonViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BoonTheme {
                BoonApp()
            }
        }
    }
}

@Composable
fun BoonApp(viewModel: BoonViewModel = viewModel()) {
    val user by viewModel.userProfile.collectAsStateWithLifecycle()
    val quests by viewModel.allQuests.collectAsStateWithLifecycle()
    val submissions by viewModel.allSubmissions.collectAsStateWithLifecycle()
    val badges by viewModel.allBadges.collectAsStateWithLifecycle()
    val certificates by viewModel.allCertificates.collectAsStateWithLifecycle()
    val creatorQuests by viewModel.allCreatorQuests.collectAsStateWithLifecycle()

    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val activeProofQuest by viewModel.activeProofQuest.collectAsStateWithLifecycle()
    val activeExperiment by viewModel.activeExperiment.collectAsStateWithLifecycle()

    val currentRiddleIdx by viewModel.currentRiddleIndex.collectAsStateWithLifecycle()
    val riddleOptions by viewModel.riddleOptions.collectAsStateWithLifecycle()
    val riddleSelectedOption by viewModel.riddleSelectedOption.collectAsStateWithLifecycle()
    val isRiddleCorrect by viewModel.isRiddleCorrect.collectAsStateWithLifecycle()
    val isHintRevealed by viewModel.isHintRevealed.collectAsStateWithLifecycle()
    val solvedRiddlesCount by viewModel.solvedRiddlesCount.collectAsStateWithLifecycle()

    val stories by viewModel.storiesList.collectAsStateWithLifecycle()
    val activeStory by viewModel.activeStory.collectAsStateWithLifecycle()
    val isStoryPlaying by viewModel.isStoryPlaying.collectAsStateWithLifecycle()

    val isBotOpen by viewModel.isBotDialogOpen.collectAsStateWithLifecycle()
    val botMessages by viewModel.botMessages.collectAsStateWithLifecycle()
    val isBotThinking by viewModel.isBotThinking.collectAsStateWithLifecycle()

    val isSettingsOpen by viewModel.isSettingsOpen.collectAsStateWithLifecycle()
    val celebrationXp by viewModel.celebrationXp.collectAsStateWithLifecycle()

    val languageCode = user?.languageCode ?: "en"

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BoonTopBar(
                user = user,
                onLanguageChange = { viewModel.setLanguage(it) },
                onOpenSettings = { viewModel.toggleSettings(true) }
            )
        },
        bottomBar = {
            BoonNavBar(
                selectedTab = currentTab,
                onTabSelected = { viewModel.selectTab(it) },
                languageCode = languageCode
            )
        },
        floatingActionButton = {
            // Floating BoonBot AI Companion Guide FAB
            FloatingActionButton(
                onClick = { viewModel.openBotDialog() },
                containerColor = EmeraldPrimary,
                contentColor = Color.White,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
                modifier = Modifier
                    .size(56.dp)
                    .offset(y = (-14).dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(EmeraldPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🤖", fontSize = 28.sp)
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Crossfade(
                targetState = currentTab,
                animationSpec = tween(250),
                label = "tab_fade"
            ) { tab ->
                when (tab) {
                    0 -> QuestsScreen(
                        quests = quests,
                        selectedCategory = selectedCategory,
                        activeProofQuest = activeProofQuest,
                        languageCode = languageCode,
                        onCategorySelected = { viewModel.setCategoryFilter(it) },
                        onStartQuest = { viewModel.openProofDialog(it) },
                        onSubmitProof = { qId, title, cat, xp, photo, audio, notes ->
                            viewModel.submitProof(qId, title, cat, xp, photo, audio, notes)
                        },
                        onCloseProof = { viewModel.closeProofDialog() }
                    )
                    1 -> ScienceLabScreen(
                        experiments = viewModel.scienceExperiments,
                        activeExperiment = activeExperiment,
                        languageCode = languageCode,
                        onOpenExperiment = { viewModel.openExperiment(it) },
                        onCloseExperiment = { viewModel.closeExperiment() },
                        onSubmitProof = { exp, photo, notes ->
                            viewModel.submitExperimentProof(exp, photo, notes)
                        }
                    )
                    2 -> MindGamesScreen(
                        languageCode = languageCode,
                        onAwardXp = { xp, reason ->
                            viewModel.submitProof(
                                questId = "game_${System.currentTimeMillis()}",
                                title = reason,
                                category = "Mind",
                                xp = xp,
                                photoUri = null,
                                audioUri = null,
                                notes = "Trained brain skills in $reason"
                            )
                        }
                    )
                    3 -> {
                        val currentRiddle = viewModel.riddlesList[currentRiddleIdx]
                        RiddlesScreen(
                            currentRiddle = currentRiddle,
                            currentIndex = currentRiddleIdx,
                            totalRiddles = viewModel.riddlesList.size,
                            options = riddleOptions,
                            selectedOption = riddleSelectedOption,
                            isCorrect = isRiddleCorrect,
                            isHintRevealed = isHintRevealed,
                            solvedCount = solvedRiddlesCount,
                            languageCode = languageCode,
                            onSelectOption = { viewModel.selectRiddleOption(it, languageCode) },
                            onNextRiddle = { viewModel.nextRiddle(languageCode) },
                            onRevealHint = { viewModel.revealRiddleHint() },
                            onSpeak = { viewModel.speakRiddle(it) }
                        )
                    }
                    4 -> StoriesScreen(
                        stories = stories,
                        activeStory = activeStory,
                        isPlayingAudio = isStoryPlaying,
                        languageCode = languageCode,
                        onOpenStory = { viewModel.openStory(it) },
                        onCloseStory = { viewModel.closeStory() },
                        onToggleAudio = { viewModel.toggleStoryAudio(it, languageCode) },
                        onCheer = { viewModel.cheerStory(it) }
                    )
                    5 -> CreatorHubScreen(
                        creatorQuests = creatorQuests,
                        languageCode = languageCode,
                        onCreateQuest = { title, cat, inst, xp, proof ->
                            viewModel.createQuest(title, cat, inst, xp, proof)
                        },
                        onLike = { viewModel.likeCreatorQuest(it) },
                        onBookmark = { viewModel.bookmarkCreatorQuest(it) }
                    )
                    6 -> ProfileScreen(
                        user = user,
                        badges = badges,
                        certificates = certificates,
                        submissions = submissions,
                        languageCode = languageCode,
                        onAvatarUpdated = { _, _ -> }
                    )
                }
            }
        }
    }

    // Welcoming Onboarding if user not yet onboarded
    if (user != null && !user!!.isOnboarded) {
        OnboardingDialog(
            languageCode = languageCode,
            onComplete = { name, avatar, photoUri ->
                viewModel.completeOnboarding(name, avatar, photoUri)
            }
        )
    }

    // BoonBot Floating AI Assistant Dialog
    if (isBotOpen) {
        BoonBotDialog(
            messages = botMessages,
            isThinking = isBotThinking,
            languageCode = languageCode,
            onSendMessage = { viewModel.sendBotMessage(it, languageCode) },
            onSpeak = { viewModel.speakBotResponse(it, languageCode) },
            onDismiss = { viewModel.closeBotDialog() }
        )
    }

    // Settings Dialog
    if (isSettingsOpen) {
        SettingsDialog(
            user = user,
            languageCode = languageCode,
            onClose = { viewModel.toggleSettings(false) },
            onLanguageChange = { viewModel.setLanguage(it) },
            onAudioChange = { sfx, music, musicOn ->
                viewModel.setAudioSettings(sfx, music, musicOn)
            },
            onResetToZero = { viewModel.resetAppToZero() }
        )
    }

    // XP & Level-up celebration fanfare dialog
    if (celebrationXp != null) {
        XpCelebrationDialog(
            xp = celebrationXp!!.first,
            questTitle = celebrationXp!!.second,
            languageCode = languageCode,
            onDismiss = { viewModel.dismissCelebration() }
        )
    }
}
