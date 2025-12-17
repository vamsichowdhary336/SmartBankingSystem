// Load dashboard data
fetch("http://localhost:8081/api/account/dashboard", {
  method: "GET",
  credentials: "include"
})
.then(res => {
  if (!res.ok) {
    throw new Error("Session expired");
  }
  return res.json();
})
.then(data => {
  document.getElementById("fullName").innerText = data.fullName;
  document.getElementById("email").innerText = data.email;
  document.getElementById("mobile").innerText = data.mobile;
  document.getElementById("accountNumber").innerText = data.accountNumber;
  document.getElementById("balance").innerText = data.balance;
})
.catch(() => {
  alert("Session expired. Please login again.");
  window.location.href = "/login/login.html";
});

// Navigation
function goTo(page) {
  if (page === "statement") {
    window.location.href = "/statement/statement.html";
  } else if (page === "transfer") {
    window.location.href = "/transfer/transfer.html";
  } else if (page === "loan") {
    window.location.href = "/loan/loan.html";
  }
}

// Logout
function logout() {
  fetch("http://localhost:8081/api/account/logout", {
    method: "POST",
    credentials: "include"
  }).finally(() => {
    window.location.href = "/login/login.html";
  });
}
