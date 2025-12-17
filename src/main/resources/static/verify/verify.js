document.getElementById("verifyForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const data = {
    email: document.getElementById("email").value,
    otp: document.getElementById("otp").value
  };

  fetch("http://localhost:8081/api/account/verify", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  })
    .then(res => res.text())
    .then(msg => {
      alert(msg);
      if (msg.toLowerCase().includes("success")) {
        window.location.href = "/login/login.html";
      }
    })
    .catch(() => alert("Verification failed"));
});
