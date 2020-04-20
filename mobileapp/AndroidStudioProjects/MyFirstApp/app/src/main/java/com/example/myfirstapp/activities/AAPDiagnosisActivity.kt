package com.example.myfirstapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myfirstapp.API.AAPDiagnosisRequests
import com.example.myfirstapp.R
import com.example.myfirstapp.Storage.SharedPrefManager
import com.quickbirdstudios.surveykit.*
import com.quickbirdstudios.surveykit.result.StepResult
import com.quickbirdstudios.surveykit.result.TaskResult
import com.quickbirdstudios.surveykit.steps.CompletionStep
import com.quickbirdstudios.surveykit.steps.QuestionStep
import com.quickbirdstudios.surveykit.survey.SurveyView
import org.json.JSONObject


class AAPDiagnosisActivity : AppCompatActivity() {
    protected lateinit var survey: SurveyView
    private lateinit var container: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aap_diagnosis)

        survey = findViewById(R.id.survey_view)
        container = findViewById(R.id.surveyContainer)
        setupSurvey(survey)
    }

    private fun setupSurvey(surveyView: SurveyView) {
        val steps = listOf(
//                InstructionStep(
//                        title = this.resources.getString(R.string.intro_title),
//                        text = this.resources5.getString(R.string.intro_text),
//                        buttonText = this.resources.getString(R.string.intro_start)
//                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question1),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.male)),
                                        TextChoice(this.resources.getString(R.string.female))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question2),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.ltTen)),
                                        TextChoice(this.resources.getString(R.string.tens)),
                                        TextChoice(this.resources.getString(R.string.twenties)),
                                        TextChoice(this.resources.getString(R.string.thirties)),
                                        TextChoice(this.resources.getString(R.string.forties)),
                                        TextChoice(this.resources.getString(R.string.fifties)),
                                        TextChoice(this.resources.getString(R.string.sixties)),
                                        TextChoice(this.resources.getString(R.string.seventyPlus))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question3),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.painRUQ)),
                                        TextChoice(this.resources.getString(R.string.painLUQ)),
                                        TextChoice(this.resources.getString(R.string.painRLQ)),
                                        TextChoice(this.resources.getString(R.string.painLLQ)),
                                        TextChoice(this.resources.getString(R.string.painUH)),
                                        TextChoice(this.resources.getString(R.string.painLH)),
                                        TextChoice(this.resources.getString(R.string.painRS)),
                                        TextChoice(this.resources.getString(R.string.painLS)),
                                        TextChoice(this.resources.getString(R.string.painC)),
                                        TextChoice(this.resources.getString(R.string.painG)),
                                        TextChoice(this.resources.getString(R.string.painRL)),
                                        TextChoice(this.resources.getString(R.string.painLL)),
                                        TextChoice(this.resources.getString(R.string.painNP))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question4),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.painRUQ)),
                                        TextChoice(this.resources.getString(R.string.painLUQ)),
                                        TextChoice(this.resources.getString(R.string.painRLQ)),
                                        TextChoice(this.resources.getString(R.string.painLLQ)),
                                        TextChoice(this.resources.getString(R.string.painUH)),
                                        TextChoice(this.resources.getString(R.string.painLH)),
                                        TextChoice(this.resources.getString(R.string.painRS)),
                                        TextChoice(this.resources.getString(R.string.painLS)),
                                        TextChoice(this.resources.getString(R.string.painC)),
                                        TextChoice(this.resources.getString(R.string.painG)),
                                        TextChoice(this.resources.getString(R.string.painRL)),
                                        TextChoice(this.resources.getString(R.string.painLL)),
                                        TextChoice(this.resources.getString(R.string.painNP))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question5),
                        answerFormat = AnswerFormat.MultipleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.movement)),
                                        TextChoice(this.resources.getString(R.string.coughing)),
                                        TextChoice(this.resources.getString(R.string.respiration)),
                                        TextChoice(this.resources.getString(R.string.food)),
                                        TextChoice(this.resources.getString(R.string.other)),
                                        TextChoice(this.resources.getString(R.string.none))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question6),
                        answerFormat = AnswerFormat.MultipleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.lyingStill)),
                                        TextChoice(this.resources.getString(R.string.vomiting)),
                                        TextChoice(this.resources.getString(R.string.antacids)),
                                        TextChoice(this.resources.getString(R.string.food)),
                                        TextChoice(this.resources.getString(R.string.other)),
                                        TextChoice(this.resources.getString(R.string.none))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question7),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.better)),
                                        TextChoice(this.resources.getString(R.string.same)),
                                        TextChoice(this.resources.getString(R.string.worse))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question8),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.hours12)),
                                        TextChoice(this.resources.getString(R.string.hours12To23)),
                                        TextChoice(this.resources.getString(R.string.hours24To48)),
                                        TextChoice(this.resources.getString(R.string.days2to7))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question9),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.intermittent)),
                                        TextChoice(this.resources.getString(R.string.steady)),
                                        TextChoice(this.resources.getString(R.string.colicky))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question10),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.moderate)),
                                        TextChoice(this.resources.getString(R.string.severe))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question11),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.yes)),
                                        TextChoice(this.resources.getString(R.string.no))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question12),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.yes)),
                                        TextChoice(this.resources.getString(R.string.no))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question13),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.yes)),
                                        TextChoice(this.resources.getString(R.string.no))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question14),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.yes)),
                                        TextChoice(this.resources.getString(R.string.no))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question15),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.yes)),
                                        TextChoice(this.resources.getString(R.string.no))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question16),
                        answerFormat = AnswerFormat.MultipleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.normal)),
                                        TextChoice(this.resources.getString(R.string.constipation)),
                                        TextChoice(this.resources.getString(R.string.diarrhoea)),
                                        TextChoice(this.resources.getString(R.string.blood)),
                                        TextChoice(this.resources.getString(R.string.mucus))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question17),
                        answerFormat = AnswerFormat.MultipleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.normal)),
                                        TextChoice(this.resources.getString(R.string.frequency)),
                                        TextChoice(this.resources.getString(R.string.dysuria)),
                                        TextChoice(this.resources.getString(R.string.dark)),
                                        TextChoice(this.resources.getString(R.string.haematuria))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question18),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.yes)),
                                        TextChoice(this.resources.getString(R.string.no))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question19),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.yes)),
                                        TextChoice(this.resources.getString(R.string.no))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question20),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.yes)),
                                        TextChoice(this.resources.getString(R.string.no))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question21),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.normal)),
                                        TextChoice(this.resources.getString(R.string.distressed)),
                                        TextChoice(this.resources.getString(R.string.anxious))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question22),
                        answerFormat = AnswerFormat.MultipleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.normal)),
                                        TextChoice(this.resources.getString(R.string.pale)),
                                        TextChoice(this.resources.getString(R.string.flushed)),
                                        TextChoice(this.resources.getString(R.string.jaundiced)),
                                        TextChoice(this.resources.getString(R.string.cyanosed))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question23),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.normal)),
                                        TextChoice(this.resources.getString(R.string.poor)),
                                        TextChoice(this.resources.getString(R.string.peristalsis))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question24),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.yes)),
                                        TextChoice(this.resources.getString(R.string.no))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question25),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.yes)),
                                        TextChoice(this.resources.getString(R.string.no))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question26),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.painRUQ)),
                                        TextChoice(this.resources.getString(R.string.painLUQ)),
                                        TextChoice(this.resources.getString(R.string.painRLQ)),
                                        TextChoice(this.resources.getString(R.string.painLLQ)),
                                        TextChoice(this.resources.getString(R.string.painUH)),
                                        TextChoice(this.resources.getString(R.string.painLH)),
                                        TextChoice(this.resources.getString(R.string.painRS)),
                                        TextChoice(this.resources.getString(R.string.painLS)),
                                        TextChoice(this.resources.getString(R.string.painC)),
                                        TextChoice(this.resources.getString(R.string.painG)),
                                        TextChoice(this.resources.getString(R.string.painRL)),
                                        TextChoice(this.resources.getString(R.string.painLL)),
                                        TextChoice(this.resources.getString(R.string.painNP))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question27),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.yes)),
                                        TextChoice(this.resources.getString(R.string.no))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question28),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.yes)),
                                        TextChoice(this.resources.getString(R.string.no))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question29),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.yes)),
                                        TextChoice(this.resources.getString(R.string.no))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question30),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.yes)),
                                        TextChoice(this.resources.getString(R.string.no))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question31),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.positive)),
                                        TextChoice(this.resources.getString(R.string.negative))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question32),
                        answerFormat = AnswerFormat.SingleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.normal)),
                                        TextChoice(this.resources.getString(R.string.decreased)),
                                        TextChoice(this.resources.getString(R.string.hyper))
                                )
                        )
                ),
                QuestionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.question33),
                        answerFormat = AnswerFormat.MultipleChoiceAnswerFormat(
                                textChoices = listOf(
                                        TextChoice(this.resources.getString(R.string.left)),
                                        TextChoice(this.resources.getString(R.string.right)),
                                        TextChoice(this.resources.getString(R.string.general)),
                                        TextChoice(this.resources.getString(R.string.mass)),
                                        TextChoice(this.resources.getString(R.string.none))
                                )
                        )
                ),
                CompletionStep(
                        title = this.resources.getString(R.string.aap_diagnosis),
                        text = this.resources.getString(R.string.completed),
                        buttonText = this.resources.getString(R.string.submit)
                )
        )

        val task = OrderedTask(steps = steps)

        surveyView.onSurveyFinish = { taskResult: TaskResult, reason: FinishReason ->
            if (reason == FinishReason.Completed) {
                val request = JSONObject(AAPDiagnosisRequests.AAP_BASE_REQUEST)
                val keys = request.keys()

                val stepResults: Iterator<StepResult> = taskResult.results.iterator()

//                taskResult.results.forEach { stepResult ->
//                    Log.e("ASDF", "answer ${stepResult.results.firstOrNull()}")
//                    container.removeAllViews()
//                }

                while (keys.hasNext() && stepResults.hasNext()) {
                    val array = request.getJSONArray(keys.next())
                    val stepResult = stepResults.next()

                    if (!stepResult.results.isNullOrEmpty()) {
                        stepResult.results.forEach { questionResult ->
                            Log.e("ASDF", "result ${questionResult.stringIdentifier}")

                            array.put(questionResult.stringIdentifier)
                        }
                    }

                    Log.e("ASDF", "json array ${array}")
                }

                Log.e("ASDF", "json request ${request}")

                // Send request
            }
        }

        val configuration = SurveyTheme(
                themeColorDark = ContextCompat.getColor(this, R.color.black),
                themeColor = ContextCompat.getColor(this, R.color.black),
                textColor = ContextCompat.getColor(this, R.color.black)
        )

        surveyView.start(task, configuration)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            survey.backPressed()
            true
        } else false
    }

    override fun onStart() {
        super.onStart()
        if (!SharedPrefManager.getInstance(this).isLoggedIn) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}