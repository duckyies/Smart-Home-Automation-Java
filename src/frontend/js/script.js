const apiUrl = 'http://localhost:8080/devices';
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
                            <p><strong>Name:</strong> ${device.deviceName}</p>
                            <p><strong>Type:</strong> ${device.deviceType}</p>
                            <p><strong>Power Consumption:</strong> ${device.basePowerConsumption} W</p>
                            <p><strong>Power Level:</strong> ${device.powerLevel}%</p>
                        </div>
                        <div class="device-controls">
                            <button class="${device.isTurnedOn ? 'off' : 'on'}" onclick="toggleDevice(${device.deviceID}, ${device.isTurnedOn})">
                                Turn ${device.isTurnedOn ? 'Off' : 'On'}
                            </button>
                        </div>
                    `;

      if (device.isTurnedOn) {
        poweredOnContainer.appendChild(deviceElement);
      } else {
        poweredOffContainer.appendChild(deviceElement);
      }
    });
  } catch (error) {
    console.error('Error fetching devices:', error);
  }
}

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

  const deviceName = document.getElementById('device-name').value;
  const deviceType = document.getElementById('device-type').value;
  const deviceGroup = document.getElementById('device-group').value;
  const location = document.getElementById('device-location').value;
  const basePowerConsumption = document.getElementById('device-power-consumption').value;
  const isTurnedOn = true;
  const powerLevel = document.getElementById('device-power-level').value;
  const maxBatteryCapacity = document.getElementById('device-max-battery-capacity').value;
  const batteryLevel = 100;
  const newDevice = { deviceName, deviceType, deviceGroup, location, isTurnedOn, batteryLevel, basePowerConsumption, maxBatteryCapacity, powerLevel };

  try {
    await fetch(apiUrl, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newDevice)
    });

    await fetchDevices();
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