package com.example.module7.fragment

import android.content.Context
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.module7.FiltersHandler
import com.example.module7.databinding.FragmentResizeBinding


class ResizeFragment : Fragment() {
    private lateinit var binding: FragmentResizeBinding
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
        binding = FragmentResizeBinding.inflate(inflater, container, false)

        binding.resizeApply.setOnClickListener {
            listener?.sendToResizeImage(binding.resizeText.text.toString().toFloat())
        }
        return binding.root
    }
}