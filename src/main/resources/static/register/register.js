// Toggle password visibility
function togglePassword() {
  const password = document.getElementById("password");
  password.type = password.type === "password" ? "text" : "password";
}

// Clear error while typing
document.getElementById("password").addEventListener("input", () => {
  document.getElementById("passwordError").style.display = "none";
});

// Form submit
document.getElementById("registerForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const fullName = document.getElementById("fullName").value.trim();
  const email = document.getElementById("email").value.trim();
  const password = document.getElementById("password").value;
  const mobile = document.getElementById("mobile").value.trim();
  const passwordError = document.getElementById("passwordError");

  // Password rule
  const passwordPattern =
    /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

  if (!passwordPattern.test(password)) {
    passwordError.style.display = "block";
    passwordError.innerText =
      "Password must contain minimum 8 characters, 1 uppercase, 1 number and 1 symbol.";
    return;
  }

  passwordError.style.display = "none";

  const data = { fullName, email, password, mobile };

  fetch("http://localhost:8081/api/account/register", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  })
    .then(res => {
      if (!res.ok) throw new Error();
      return res.text();
    })
    .then(() => {
      alert("OTP sent to email");
      window.location.href = "/verify/verify.html";
    })
    .catch(() => {
      alert("Registration failed");
    });
});
