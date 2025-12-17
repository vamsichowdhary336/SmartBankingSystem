fetch("http://localhost:8081/api/account/statement", {
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
  const tbody = document.getElementById("statementBody");

  if (data.length === 0) {
    tbody.innerHTML = "<tr><td colspan='4'>No transactions found</td></tr>";
    return;
  }

  data.forEach(tx => {
    const row = document.createElement("tr");

    row.innerHTML = `
      <td>${tx.type}</td>
      <td>${tx.amount}</td>
      <td>${new Date(tx.date).toLocaleString()}</td>
      <td>${tx.balance}</td>
    `;

    tbody.appendChild(row);
  });
})
.catch(() => {
  alert("Session expired. Please login again.");
  window.location.href = "/login/login.html";
});

function goBack() {
  window.location.href = "/dashboard/dashboard.html";
}
