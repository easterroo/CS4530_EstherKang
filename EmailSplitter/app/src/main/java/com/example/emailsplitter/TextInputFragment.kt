package com.example.emailsplitter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.emailsplitter.databinding.FragmentTextInputBinding

class TextInputFragment : Fragment() {
    private var _binding: FragmentTextInputBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTextInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.splitButton.setOnClickListener {
            val email = binding.emailInput.text.toString()
            val pieces = email.split('@')

            if (pieces.size != 2 || pieces.any(String::isEmpty)) {
                Toast.makeText(requireContext(), "Invalid email!", Toast.LENGTH_SHORT).show()
            } else {
                val result = ResultFragment().apply {
                    arguments = Bundle().apply {
                        putString("username", pieces[0])
                        putString("domain", pieces[1])
                    }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, result)
                    .commit()
                Toast.makeText(requireContext(), "Data passed!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}