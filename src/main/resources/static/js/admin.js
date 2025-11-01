console.log("admin panel loaded");

async function api(path) {
  const res = await fetch(path);
  if (!res.ok) throw new Error("API error");
  const ct = res.headers.get("content-type") || "";
  return ct.includes("application/json") ? res.json() : res.text();
}

async function loadStats() {
  try {
    const s = await api("/admin/stats");
    document.getElementById("stats").textContent = s;
  } catch (e) {
    document.getElementById("stats").textContent = "";
  }
}

async function loadUsers() {
  const body = document.getElementById("adminUsersBody");
  body.innerHTML = '<tr><td colspan="5">Loading...</td></tr>';
  try {
    const users = await api("/admin/users");
    if (!Array.isArray(users) || users.length === 0) {
      body.innerHTML = '<tr><td colspan="5">No users.</td></tr>';
      return;
    }
    body.innerHTML = users
      .map((u) => {
        const isAdmin = (u.roleList || []).includes("ROLE_ADMIN");
        return `<tr>
        <td>${u.name || ""}</td>
        <td>${u.email || ""}</td>
        <td>${u.enabled ? "Yes" : "No"}</td>
        <td>${(u.roleList || []).join(", ")}</td>
        <td class="flex gap-2">
          <button class="btn btn-primary" data-action="${
            u.enabled ? "disable" : "enable"
          }" data-id="${u.userId}">${u.enabled ? "Disable" : "Enable"}</button>
          <button class="btn btn-gray" data-action="${
            isAdmin ? "demote" : "promote"
          }" data-id="${u.userId}">${isAdmin ? "Demote" : "Promote"}</button>
          <button class="btn btn-red" data-action="delete" data-id="${
            u.userId
          }">Delete</button>
        </td>
      </tr>`;
      })
      .join("");
  } catch (e) {
    body.innerHTML = '<tr><td colspan="5">Failed to load users.</td></tr>';
  }
}

document.addEventListener("click", async (e) => {
  const btn = e.target.closest("button[data-action]");
  if (!btn) return;
  const id = btn.getAttribute("data-id");
  const action = btn.getAttribute("data-action");
  const paths = {
    enable: `/admin/enable/${id}`,
    disable: `/admin/disable/${id}`,
    promote: `/admin/promote/${id}`,
    demote: `/admin/demote/${id}`,
    delete: `/admin/delete/${id}`,
  };
  try {
    await api(paths[action]);
    await loadUsers();
    await loadStats();
  } catch (err) {
    alert("Action failed");
  }
});

document.getElementById("refreshBtn")?.addEventListener("click", async () => {
  await loadUsers();
  await loadStats();
});

// initial
loadUsers();
loadStats();
