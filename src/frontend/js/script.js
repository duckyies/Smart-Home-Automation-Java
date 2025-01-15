const apiUrl = 'http://localhost:8080/devices';

// Function to fetch and display devices
async function fetchDevices() {
  try {
    const response = await fetch(apiUrl);
    const devices = await response.json();

    const poweredOnContainer = document.getElementById('powered-on-devices');
    const poweredOffContainer = document.getElementById('powered-off-devices');

    poweredOnContainer.innerHTML = '';
    poweredOffContainer.innerHTML = '';

    devices.forEach(device => {
      const deviceElement = document.createElement('div');
      deviceElement.className = 'device';

      deviceElement.innerHTML = `
                        <div class="device-info">
                            <p><strong>Name:</strong> ${device.name}</p>
                            <p><strong>Type:</strong> ${device.type}</p>
                            <p><strong>Power Consumption:</strong> ${device.basePowerConsumption} W</p>
                            <p><strong>Power Level:</strong> ${device.powerLevel}%</p>
                        </div>
                        <div class="device-controls">
                            <button class="${device.isPoweredOn ? 'off' : 'on'}" onclick="toggleDevice(${device.id}, ${device.isPoweredOn})">
                                Turn ${device.isPoweredOn ? 'Off' : 'On'}
                            </button>
                        </div>
                    `;

      if (device.isPoweredOn) {
        poweredOnContainer.appendChild(deviceElement);
      } else {
        poweredOffContainer.appendChild(deviceElement);
      }
    });
  } catch (error) {
    console.error('Error fetching devices:', error);
  }
}

// Function to toggle device power state
async function toggleDevice(id, isPoweredOn) {
  try {
    const endpoint = `${apiUrl}/id/${id}/${isPoweredOn ? 'off' : 'on'}`;
    await fetch(endpoint, { method: 'PUT' });
    fetchDevices();
  } catch (error) {
    console.error('Error toggling device:', error);
  }
}

// Handle Add Device form submission
document.getElementById('add-device-form').addEventListener('submit', async (e) => {
  e.preventDefault();

  const name = document.getElementById('device-name').value;
  const type = document.getElementById('device-type').value;
  const group = document.getElementById('device-group').value;
  const location = document.getElementById('device-location').value;

  const newDevice = { name, type, group, location };

  try {
    await fetch(apiUrl, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newDevice)
    });

    fetchDevices();
  } catch (error) {
    console.error('Error adding device:', error);
  }
});

// Handle Add Location form submission
document.getElementById('add-location-form').addEventListener('submit', async (e) => {
  e.preventDefault();

  const locationName = document.getElementById('location-name').value;

  // Add location handling logic here (if API supports adding locations)
  console.log('New location added:', locationName);
});

// Initial fetch of devices
fetchDevices();