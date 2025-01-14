const apiUrl = 'http://localhost:8080/devices';

// Fetch and display all devices
async function fetchDevices() {
  const response = await fetch(apiUrl);
  const devices = await response.json();
  const deviceTable = document.getElementById('deviceTable');

  deviceTable.innerHTML = devices.map(device => `
    <tr>
      <td>${device.id}</td>
      <td>${device.type}</td>
      <td>${device.status ? 'ON' : 'OFF'}</td>
      <td>
        <button onclick="toggleDevice('${device.id}')">
          Toggle
        </button>
        <button onclick="removeDevice('${device.id}')">
          Remove
        </button>
      </td>
    </tr>
  `).join('');
}

// Add a new device
document.getElementById('addDeviceForm').addEventListener('submit', async (e) => {
  e.preventDefault();

  const id = document.getElementById('deviceId').value;
  const type = document.getElementById('deviceType').value;

  await fetch(apiUrl, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ id, type })
  });

  fetchDevices();
});

// Toggle a device
async function toggleDevice(id) {
  await fetch(`${apiUrl}/${id}/toggle`, { method: 'PUT' });
  fetchDevices();
}

// Remove a device
async function removeDevice(id) {
  await fetch(`${apiUrl}/${id}`, { method: 'DELETE' });
  fetchDevices();
}

// Initialize the UI
fetchDevices();
