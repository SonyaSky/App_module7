package com.example.module7.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.module7.FiltersHandler
import com.example.module7.databinding.FragmentFiltersBinding

class FaceFilterFragment : Fragment() {
    private lateinit var binding: FragmentFiltersBinding
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
        binding = FragmentFiltersBinding.inflate(inflater, container, false)

        setOnClickListeners()

        return binding.root
    }

    private fun setOnClickListeners() {
        if (listener != null) {
            val listOfPhotos = listener!!.takePhotosForButtons()
            if (listOfPhotos != null) {
                val listOfButtons = listOf(
                    binding.invert,
                    binding.gray,
                    binding.bnw,
                    binding.dark,
                    binding.light,
                    binding.sepia,
                    binding.yellow,
                    binding.sage,
                    binding.orange,
                    binding.pink,
                    binding.purple,
                    binding.blue,
                    binding.red,
                    binding.green
                )
                listOfButtons.forEachIndexed { index, button ->
                    if (listOfPhotos[index] != null) {
                        button.setImageBitmap(listOfPhotos[index])
                    }
                }
                binding.invert.setOnClickListener {
                    listener?.sendToInversionFilter()
                }

                binding.gray.setOnClickListener {
                    listener?.sendToGrayscaleFilter()
                }

                binding.bnw.setOnClickListener {
                    listener?.sendToBNWFilter()
                }

                binding.dark.setOnClickListener {
                    listener?.sendToLightFilter("dark")
                }

                binding.light.setOnClickListener {
                    listener?.sendToLightFilter("light")
                }

                binding.sepia.setOnClickListener {
                    listener?.sendToSepiaFilter()
                }

                binding.yellow.setOnClickListener {
                    listener?.sendToTintFilter("yellow")
                }

                binding.sage.setOnClickListener {
                    listener?.sendToTintFilter("green")
                }

                binding.orange.setOnClickListener {
                    listener?.sendToTintFilter("orange")
                }

                binding.pink.setOnClickListener {
                    listener?.sendToTintFilter("pink")
                }

                binding.purple.setOnClickListener {
                    listener?.sendToTintFilter("purple")
                }

                binding.blue.setOnClickListener {
                    listener?.sendToColorFilter("blue")
                }

                binding.green.setOnClickListener {
                    listener?.sendToColorFilter("green")
                }
                binding.red.setOnClickListener {
                    listener?.sendToColorFilter("red")
                }
            }
        }
    }
}