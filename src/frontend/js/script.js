const apiUrl = 'http://localhost:8080/devices';

async function fetchDevices() {
  const response = await fetch(apiUrl);
  const devices = await response.json();
  const deviceTable = document.getElementById('deviceTable');
  deviceTable.innerHTML = devices.map(device => `
    <tr>
      <td>${device.name}</td>
      <td>${device.powerConsumption}</td>
      <td>${device.powerState ? 'On' : 'Off'}</td>
      <td>
        <button onclick="toggleDevice('${device.id}', ${device.powerState})">
          ${device.powerState ? 'Turn Off' : 'Turn On'}
        </button>
        <button onclick="removeDevice('${device.id}')">Remove</button>
      </td>
    </tr>
  `).join('');
}

document.getElementById('addDeviceForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  const deviceName = document.getElementById('deviceName').value;
  const powerConsumption = parseInt(document.getElementById('powerConsumption').value);
  
  await fetch(apiUrl, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ name: deviceName, powerConsumption, powerState: false })
  });

  document.getElementById('addDeviceForm').reset();
  fetchDevices();
});

async function toggleDevice(id, currentState) {
  await fetch(`${apiUrl}/${id}/toggle`, { method: 'PUT' });
  fetchDevices();
}

async function removeDevice(id) {
  await fetch(`${apiUrl}/${id}`, { method: 'DELETE' });
  fetchDevices();
}

fetchDevices();
