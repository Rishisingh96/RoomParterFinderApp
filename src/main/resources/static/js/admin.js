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
  body.innerHTML = `
    <tr>
      <td colspan="5" class="px-6 py-8 text-center text-gray-500 dark:text-gray-400">
        <div class="flex flex-col items-center gap-2">
          <i class="fa-solid fa-spinner fa-spin text-2xl text-blue-500"></i>
          <span>Loading users...</span>
        </div>
      </td>
    </tr>`;
  try {
    const users = await api("/admin/users");
    if (!Array.isArray(users) || users.length === 0) {
      body.innerHTML = `
        <tr>
          <td colspan="5" class="px-6 py-8 text-center text-gray-500 dark:text-gray-400">
            <div class="flex flex-col items-center gap-2">
              <i class="fa-solid fa-users-slash text-2xl text-gray-400"></i>
              <span>No users found.</span>
            </div>
          </td>
        </tr>`;
      return;
    }
    body.innerHTML = users
      .map((u, index) => {
        const isAdmin = (u.roleList || []).includes("ROLE_ADMIN");
        const statusClass = u.enabled ? "status-enabled" : "status-disabled";
        const statusText = u.enabled ? "✓ Enabled" : "✗ Disabled";
        
        const roleBadges = (u.roleList || []).map(role => {
          const roleClass = role === "ROLE_ADMIN" ? "role-admin" : "role-user";
          const roleName = role.replace("ROLE_", "");
          return `<span class="role-badge ${roleClass}">${roleName}</span>`;
        }).join("");
        
        return `
        <tr class="user-row animate-slide-in" style="animation-delay: ${index * 0.05}s">
          <td class="px-6 py-4 font-medium text-gray-900 dark:text-white">${u.name || "N/A"}</td>
          <td class="px-6 py-4 text-gray-600 dark:text-gray-400">${u.email || ""}</td>
          <td class="px-6 py-4">
            <span class="status-badge ${statusClass}">${statusText}</span>
          </td>
          <td class="px-6 py-4">${roleBadges}</td>
          <td class="px-6 py-4">
            <div class="flex flex-wrap gap-2">
              <button 
                class="btn-action px-3 py-1.5 rounded-lg text-xs font-bold text-white transition-all ${
                  u.enabled 
                    ? "bg-gradient-to-r from-orange-500 to-red-500 hover:from-orange-600 hover:to-red-600" 
                    : "bg-gradient-to-r from-green-500 to-emerald-500 hover:from-green-600 hover:to-emerald-600"
                }" 
                data-action="${u.enabled ? "disable" : "enable"}" 
                data-id="${u.userId}"
                title="${u.enabled ? "Disable user" : "Enable user"}"
              >
                <i class="fa-solid ${u.enabled ? "fa-ban" : "fa-check"} mr-1"></i>
                ${u.enabled ? "Disable" : "Enable"}
              </button>
              <button 
                class="btn-action px-3 py-1.5 rounded-lg text-xs font-bold text-white bg-gradient-to-r from-purple-500 to-indigo-500 hover:from-purple-600 hover:to-indigo-600 transition-all" 
                data-action="${isAdmin ? "demote" : "promote"}" 
                data-id="${u.userId}"
                title="${isAdmin ? "Remove admin role" : "Grant admin role"}"
              >
                <i class="fa-solid ${isAdmin ? "fa-arrow-down" : "fa-arrow-up"} mr-1"></i>
                ${isAdmin ? "Demote" : "Promote"}
              </button>
              <button 
                class="btn-action px-3 py-1.5 rounded-lg text-xs font-bold text-white bg-gradient-to-r from-red-500 to-pink-500 hover:from-red-600 hover:to-pink-600 transition-all" 
                data-action="delete" 
                data-id="${u.userId}"
                title="Delete user permanently"
              >
                <i class="fa-solid fa-trash mr-1"></i>
                Delete
              </button>
            </div>
          </td>
        </tr>`;
      })
      .join("");
  } catch (e) {
    body.innerHTML = `
      <tr>
        <td colspan="5" class="px-6 py-8 text-center text-red-500 dark:text-red-400">
          <div class="flex flex-col items-center gap-2">
            <i class="fa-solid fa-triangle-exclamation text-2xl"></i>
            <span>Failed to load users. Please try again.</span>
          </div>
        </td>
      </tr>`;
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
  
  // Add loading state to button
  const originalContent = btn.innerHTML;
  btn.innerHTML = '<i class="fa-solid fa-spinner fa-spin"></i>';
  btn.disabled = true;
  
  try {
    await api(paths[action]);
    
    // Show success feedback
    const row = btn.closest("tr");
    if (row) {
      row.style.transition = "all 0.3s ease";
      row.style.background = "linear-gradient(90deg, rgba(16, 185, 129, 0.1), rgba(5, 150, 105, 0.1))";
      setTimeout(() => {
        row.style.background = "";
      }, 500);
    }
    
    await loadUsers();
    await loadStats();
  } catch (err) {
    // Show error feedback
    const row = btn.closest("tr");
    if (row) {
      row.style.transition = "all 0.3s ease";
      row.style.background = "linear-gradient(90deg, rgba(239, 68, 68, 0.1), rgba(220, 38, 38, 0.1))";
      setTimeout(() => {
        row.style.background = "";
      }, 500);
    }
    alert("Action failed. Please try again.");
  } finally {
    // Restore button state
    btn.innerHTML = originalContent;
    btn.disabled = false;
  }
});

// Search functionality
document.getElementById("adminSearch")?.addEventListener("input", async (e) => {
  const searchTerm = e.target.value.toLowerCase();
  const rows = document.querySelectorAll("#adminUsersBody tr");
  
  rows.forEach(row => {
    const text = row.textContent.toLowerCase();
    if (text.includes(searchTerm)) {
      row.style.display = "";
    } else {
      row.style.display = "none";
    }
  });
});

document.getElementById("refreshBtn")?.addEventListener("click", async () => {
  const btn = document.getElementById("refreshBtn");
  const originalContent = btn.innerHTML;
  btn.innerHTML = '<i class="fa-solid fa-spinner fa-spin mr-2"></i>Refreshing...';
  btn.disabled = true;
  
  try {
    await loadUsers();
    await loadStats();
  } finally {
    btn.innerHTML = originalContent;
    btn.disabled = false;
  }
});

// initial
loadUsers();
loadStats();
