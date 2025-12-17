document.getElementById("loanForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const amount = document.getElementById("amount").value;
  const message = document.getElementById("message");

  fetch("http://localhost:8081/api/loan/apply", {
    method: "POST",
    credentials: "include",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ amount: amount })
  })
    .then(res => res.text())
    .then(data => {
      message.style.color = "green";
      message.innerText = data;
      document.getElementById("amount").value = "";
    })
    .catch(() => {
      message.style.color = "red";
      message.innerText = "Please login first";
    });
});

function goBack() {
  window.location.href = "/dashboard/dashboard.html";
}
