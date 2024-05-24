package com.example.module7.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.module7.FiltersHandler
import com.example.module7.databinding.FragmentRotateBinding


class RotateFragment : Fragment() {
    private lateinit var binding: FragmentRotateBinding
    private var listener: FiltersHandler? = null

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
        binding = FragmentRotateBinding.inflate(inflater, container, false)


        binding.rangeSlider.addOnChangeListener { slider, value, fromUser ->
            listener?.sendToRotateImage(value)
        }
        return binding.root
    }
}