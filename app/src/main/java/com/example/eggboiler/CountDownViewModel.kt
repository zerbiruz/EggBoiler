package com.example.eggboiler

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class CountDownViewModel : ViewModel() {

    private lateinit var timer: CountDownTimer
    val isRunning = MutableLiveData<Boolean>(false)
    val maxTime = MutableLiveData<Long>()
    val currentTime = MutableLiveData<Long>()
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    fun startCountdown() {
        timer = object : CountDownTimer(maxTime.value!!, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                currentTime.value = millisUntilFinished/1000L
            }

            override fun onFinish() {
                currentTime.value = 0L
            }
        }.start()
    }

    fun stopCountdown() {
        timer.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }
}