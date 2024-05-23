package com.example.module7.fragment

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.example.module7.FiltersHandler
import com.example.module7.databinding.FragmentMaskingBinding



class MaskingFragment : Fragment() {
    private lateinit var binding: FragmentMaskingBinding
    private var listener: FiltersHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMaskingBinding.inflate(inflater, container, false)


        binding.effectSeekBar.addOnChangeListener { slider, value, fromUser ->
            listener?.sendToRotateImage(value)
        }
        return binding.root
    }
}