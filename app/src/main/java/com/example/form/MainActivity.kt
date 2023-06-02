package com.example.form

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.quicksettings.Tile
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //var viewmodel = ViewModelProvider(this)[MainViewModel::class.java]
        val etFname = findViewById<TextInputLayout>(R.id.etFname)
        etFname.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if(s?.length == 0) {
                    etFname.error = "Required field"
                }
                else {
                    etFname.error = null
                }
            }
        })

        val etLname = findViewById<TextInputLayout>(R.id.etLname)
        etLname.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if(s?.length == 0) {
                    etLname.error = "Required field"
                }
                else {
                    etLname.error = null
                }
            }
        })

        val etUname = findViewById<TextInputLayout>(R.id.etUname)
        etUname.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if(s?.length == 0) {
                    etUname.error = "Required field"
                }
                else {
                    etUname.error = null
                }
            }
        })

        val etIin = findViewById<TextInputLayout>(R.id.etIin)
        etIin.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(start+count in 1..11){
                    etIin.error = "Less digits"
                }
                else if(start+count == 12) {
                    etIin.error = isCorrectIin(s.toString())
                }
                else if(start+count == 0) {
                    etIin.error = "Required field"
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        val etPhone = findViewById<TextInputLayout>(R.id.etPhone)
        etPhone.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if(s?.length == 0) {
                    etPhone.error = "Required field"
                }
                else if (s?.length!! < 10){
                    etPhone.error = "Less digits"
                }
                else {
                    etPhone.error = null
                }
            }
        })

        val etBirthday = findViewById<TextInputLayout>(R.id.etBirthday)
        etBirthday.editText?.setOnClickListener {
            showCalendar(etBirthday.editText!!)
        }
        etBirthday.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s?.length!! >= 6 && s.toString().get(5) != '/'){
                    etBirthday.error = "Must be in DD/MM/YYYY format"
                }
                else if (s.length >= 3 && s.toString().get(2) != '/'){
                    etBirthday.error = "Must be in DD/MM/YYYY format"
                }
                else if (s.length in 1..9){
                    etBirthday.error = "Less digits"
                }
                else if (s.length == 0 || s.length == 10) {
                    etBirthday.error = null
                }
            }
        })

        val etPassword = findViewById<TextInputLayout>(R.id.etPassword)
        etPassword.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if(s?.length == 0) {
                    etPassword.error = "Required field"
                }
                else if (s?.length!! < 8){
                    etPassword.error = "Less characters"
                }
                else {
                    etPassword.error = isCorrectPassword(s.toString())
                }
            }
        })

        val etConfirm = findViewById<TextInputLayout>(R.id.etConfirm)
        etConfirm.editText?.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if(s?.length == 0) {
                    etConfirm.error = "Required field"
                }
                else {
                    if(s.toString() != etPassword.editText?.text.toString()){
                        etConfirm.error = "Not same password"
                    }
                    else {
                        etConfirm.error = null
                    }
                }
            }
        })
    }

    private fun showCalendar(edittext: EditText) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog (
            this, {view, years, months, days ->
                val dat = ((if(days < 10) "0"  else "") + days + "/" + (if(months + 1 < 10) "0"  else "") + (months + 1) + "/" + years)
                edittext.setText(dat)
            }, year, month, day)
        datePickerDialog.show()
    }

    private fun isCorrectIin(iin: String) : String? {
        var coef = 1*(iin[0]-'0') + 2*(iin[1]-'0') + 3*(iin[2]-'0') + 4*(iin[3]-'0') + 5*(iin[4]-'0')
                + 6*(iin[5]-'0') + 7*(iin[6]-'0') + 8*(iin[7]-'0') + 9*(iin[8]-'0')
             + 10*(iin[9]-'0') + 11*(iin[10]-'0')
        if(coef % 11 == 10) {
            coef = 3*(iin[0]-'0') + 4*(iin[1]-'0') + 5*(iin[2]-'0') + 6*(iin[3]-'0') + 7*(iin[4]-'0')
                + 8*(iin[5]-'0') + 9*(iin[6]-'0') + 10*(iin[7]-'0') + 11*(iin[8]-'0')
                + 1*(iin[9]-'0') + 2*(iin[10]-'0')
        }
        if(coef % 11 == 10) {
            return "Such IIN does not exist"
        }
        else if(coef % 11 != iin[11]-'0') {
            return "Such IIN does not exist"
        }
        return null
    }

    private fun isCorrectPassword(p: String): String? {
        var isThereUpper = false
        var isThereSpecial = false
        for(i in 0 until p.length){
            if(p[i] in 'A'..'Z') {
                isThereUpper = true
            }
        }
        for(i in 0 until p.length){
            if(p[i] == '!' || p[i] == '/' || p[i] == '$' || p[i] == '%' || p[i] == '^') {
                isThereSpecial = true
            }
        }
        if(!isThereSpecial){
            return "Must contain at least one of (!/$%^)"
        }
        else if(!isThereUpper) {
            return "Must contain uppercase letter"
        }
        return null
    }

}


