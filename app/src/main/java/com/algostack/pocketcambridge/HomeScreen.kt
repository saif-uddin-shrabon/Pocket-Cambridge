package com.algostack.pocketcambridge

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.algostack.pocketcambridge.databinding.FragmentHomeScreenBinding
import com.algostack.pocketcambridge.utils.AlertDaialog
import com.algostack.pocketcambridge.utils.NetworkUtlist


@Suppress("UNUSED_EXPRESSION")
class HomeScreen : Fragment() {

     private var _binding: FragmentHomeScreenBinding? = null
        private val binding get() = _binding!!
     var doubleBackToExitPressedOnce = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
            return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!NetworkUtlist.isInternetConnected(requireContext())){
             //  AlertDaialog.noInternetConnectionAlertBox(requireContext())
            findNavController().navigate(R.id.action_homeScreen_to_nointernet)
        }else{
            binding.camwebView.apply {
                settings.javaScriptEnabled = true
                settings.setSupportZoom(true)
                settings.builtInZoomControls = true
                settings.displayZoomControls = false
                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()

                loadUrl("https://dictionary.cambridge.org/")
            }
        }

        binding.camwebView.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                // Show a progress bar or loading indicator
               binding.progressBar.visibility = View.VISIBLE
                binding.camwebView.visibility = View.GONE
            }


            override fun onPageFinished(view: WebView?, url: String?) {
                // Hide the progress bar
                binding.progressBar.visibility = View.GONE
                binding.camwebView.visibility = View.VISIBLE


            }

            override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
                // Handle errors, e.g., display an error message
                Toast.makeText(requireContext(), "Error: $description", Toast.LENGTH_SHORT).show()
            }
        }


        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.camwebView.canGoBack()) {
                    binding.camwebView.goBack()
                } else {
                    if (doubleBackToExitPressedOnce) {
                        requireActivity().finish()
                        return
                    }

                    doubleBackToExitPressedOnce = true
                    Toast.makeText(requireContext(), "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

                    Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callBack)


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}