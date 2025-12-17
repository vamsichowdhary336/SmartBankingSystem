function login() {
  const email = document.getElementById("email").value.trim();
  const password = document.getElementById("password").value.trim();
  const msg = document.getElementById("msg");

  fetch("http://localhost:8081/api/account/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    credentials: "include",
    body: JSON.stringify({ email, password })
  })
    .then(res => res.text().then(text => {
      if (!res.ok) throw new Error(text);
      return text;
    }))
    .then(() => {
      msg.style.color = "green";
      msg.innerText = "Login successful";
      window.location.href = "../Dashboard/dashboard.html";
    })
    .catch(err => {
      msg.style.color = "red";
      msg.innerText = err.message || "Invalid email or password";
    });
}

// 👁 Show / Hide password
function togglePassword() {
  const pwd = document.getElementById("password");
  pwd.type = pwd.type === "password" ? "text" : "password";
}
