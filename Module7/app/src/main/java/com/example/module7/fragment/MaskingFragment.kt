package com.example.module7.fragment

<<<<<<< Updated upstream
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
=======
import android.content.Context
import android.os.Bundle
import android.util.Log
>>>>>>> Stashed changes
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< Updated upstream
import androidx.appcompat.widget.AppCompatImageView
=======
>>>>>>> Stashed changes
import com.example.module7.FiltersHandler
import com.example.module7.databinding.FragmentMaskingBinding



class MaskingFragment : Fragment() {
    private lateinit var binding: FragmentMaskingBinding
    private var listener: FiltersHandler? = null
<<<<<<< Updated upstream
=======
    private var effect: Double = 0.0
    private var radius: Float = 0.0F
    private var threshold: Float = 0.0F
>>>>>>> Stashed changes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

<<<<<<< Updated upstream
=======
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FiltersHandler) {
            listener = context
        }
    }


>>>>>>> Stashed changes
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMaskingBinding.inflate(inflater, container, false)

<<<<<<< Updated upstream

        binding.effectSeekBar.addOnChangeListener { slider, value, fromUser ->
            listener?.sendToRotateImage(value)
=======
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
>>>>>>> Stashed changes
        }
        return binding.root
    }
}