package com.example.module7.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.module7.FiltersHandler
import com.example.module7.databinding.FragmentMaskingBinding



class MaskingFragment : Fragment() {
    private lateinit var binding: FragmentMaskingBinding
    private var listener: FiltersHandler? = null
    private var effect: Double = 0.0
    private var radius: Float = 0.0F
    private var threshold: Float = 0.0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FiltersHandler) {
            listener = context
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMaskingBinding.inflate(inflater, container, false)

        binding.effectSeekBar.addOnChangeListener { slider, value, fromUser ->
            effect = value.toDouble()
        }

        binding.radiusSeekBar.addOnChangeListener { slider, value, fromUser ->
            radius = value
        }

        binding.thresholdSeekBar.addOnChangeListener { slider, value, fromUser ->
            threshold = value
        }

        binding.maskingApply.setOnClickListener {
            listener?.sendToMasking(radius.toInt(), effect/100.0, threshold.toInt())
        }
        return binding.root
    }
}