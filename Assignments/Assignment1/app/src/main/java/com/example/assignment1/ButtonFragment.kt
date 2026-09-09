package com.example.assignment1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.assignment1.databinding.FragmentButtonBinding

/**
 * Fragment that holds the five buttons with text of different colors in it
 */
class ButtonFragment : Fragment() {
    private var _binding: FragmentButtonBinding? = null
    private val binding get() = _binding!!

    /**
     * Initializes the create view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentButtonBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Initializes the button variables and creates the onClick functions for each, passing their text
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttons = listOf(
            binding.button1,
            binding.button2,
            binding.button3,
            binding.button4,
            binding.button5
        )

        buttons.forEach { button ->
            button.setOnClickListener {
                val selectedText = button.text.toString()
                val displayFragment = DisplayFragment().apply {
                    arguments = Bundle().apply {
                        putString("selectedText", selectedText)
                    }
                }

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.main, displayFragment)
                    .addToBackStack(null)   // enables built-in back button/gesture
                    .commit()
            }
        }
    }
}