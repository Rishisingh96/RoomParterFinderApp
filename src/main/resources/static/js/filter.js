const data = {
  Delhi: {
    Delhi: {
      areas: ["Connaught Place", "Rohini", "Saket", "Dwarka", "Karol Bagh"],
      colleges: [
        "DU North",
        "DU South",
        "JNU",
        "IPU",
        "NSIT",
        "DTU",
        "Jamia",
        "SRCC",
        "Zakir Husain",
        "Lady Shri Ram",
        "nan",
      ],
    },
  },
  "Uttar Pradesh": {
    Lucknow: {
      areas: [
        "Hazratganj",
        "Gomti Nagar",
        "Alambagh",
        "Indira Nagar",
        "Charbagh",
      ],
      colleges: [
        "IIM Lucknow",
        "LU",
        "Integral",
        "BBD",
        "SRMU",
        "RMLNLU",
        "Amity",
        "AKTU",
        "Era",
        "Jaipuria",
        "nan",
      ],
    },
    Noida: {
      areas: ["Sector 62", "Sector 18", "Sector 15", "Sector 71", "Sector 137"],
      colleges: [
        "Amity",
        "GL Bajaj",
        "Sharda",
        "JSS",
        "Jaypee",
        "Galgotias",
        "NIET",
        "IIMT",
        "BIMTECH",
        "Symbiosis",
        "nan",
      ],
    },
  },
  "Madhya Pradesh": {
    Indore: {
      areas: ["Vijay Nagar", "Palasia", "Rau", "Bhawarkuan", "MG Road"],
      colleges: [
        "IIM Indore",
        "DAVV",
        "Medicaps",
        "IPS",
        "Acropolis",
        "Renaissance",
        "SAGE",
        "Prestige",
        "LNCT",
        "SVITS",
        "nan",
      ],
    },
    Bhopal: {
      areas: ["MP Nagar", "TT Nagar", "Kolar", "Habibganj", "Arera Colony"],
      colleges: [
        "MANIT",
        "BU",
        "LNCT",
        "Oriental",
        "RGPV",
        "IES",
        "RKDF",
        "VNS",
        "SIRT",
        "AISECT",
        "nan",
      ],
    },
  },
};

const stateSel = document.getElementById("state"),
  citySel = document.getElementById("city"),
  areaSel = document.getElementById("area"),
  collegeSel = document.getElementById("college");

stateSel.innerHTML += Object.keys(data)
  .map((s) => `<option value="${s}">${s}</option>`)
  .join("");

stateSel.onchange = () => {
  citySel.innerHTML =
    `<option value="">Select City</option>` +
    Object.keys(data[stateSel.value] || {})
      .map((c) => `<option value="${c}">${c}</option>`)
      .join("");
  areaSel.innerHTML = `<option value="">Select Area</option>`;
  collegeSel.innerHTML = `<option value="">Select College</option>`;
};

const ageSelect = document.getElementById("age");
for (let i = 10; i <= 70; i++) {
  const option = document.createElement("option");
  option.value = i;
  option.textContent = i;
  ageSelect.appendChild(option);
}

citySel.onchange = () => {
  const selected = data[stateSel.value]?.[citySel.value];
  if (selected) {
    // Populate areas
    areaSel.innerHTML =
      `<option value="">Select Area</option>` +
      (selected.areas || [])
        .map((a) => `<option value="${a}">${a}</option>`)
        .join("");

    // Populate colleges
    collegeSel.innerHTML =
      `<option value="">Select College</option>` +
      (selected.colleges || [])
        .map((c) => `<option value="${c}">${c}</option>`)
        .join("");
  } else {
    // Reset area and college dropdowns if no city is selected
    areaSel.innerHTML = `<option value="">Select Area</option>`;
    collegeSel.innerHTML = `<option value="">Select College</option>`;
  }
};
