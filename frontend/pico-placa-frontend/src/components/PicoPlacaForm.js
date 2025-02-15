import React, { useState } from 'react';
import axios from 'axios';

function PicoPlacaForm() {
  const [licensePlate, setLicensePlate] = useState('');
  const [date, setDate] = useState('');
  const [time, setTime] = useState('');
  const [result, setResult] = useState(null);
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Basic client-side validation
    const plateRegex = /^[A-Z]{3}-\d{4}$/;
    if (!plateRegex.test(licensePlate)) {
      setError('License plate must be in the format ABC-1234');
      return;
    }
    if (!date) {
      setError('Please select a date.');
      return;
    }
    if (!time) {
      setError('Please select a time.');
      return;
    }
    setError('');

    try {
      // Adjust the URL if your backend is hosted elsewhere
      const response = await axios.get('http://localhost:8080/api/check', {
        params: { licensePlate, date, time },
      });
      console.log(response);
      setResult(response.data);
    } catch (err) {
      setError('Error fetching data from the server');
    }
  };

  return (
    <div style={{ margin: '20px auto', maxWidth: '400px' }}>
      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: '15px' }}>
          <label>
            License Plate:
            <input
              type="text"
              value={licensePlate}
              onChange={(e) => setLicensePlate(e.target.value)}
              placeholder="ABC-1234"
              style={{ width: '100%', padding: '8px', marginTop: '5px' }}
            />
          </label>
        </div>
        <div style={{ marginBottom: '15px' }}>
          <label>
            Date:
            <input
              type="date"
              value={date}
              onChange={(e) => setDate(e.target.value)}
              style={{ width: '100%', padding: '8px', marginTop: '5px' }}
            />
          </label>
        </div>
        <div style={{ marginBottom: '15px' }}>
          <label>
            Time:
            <input
              type="time"
              value={time}
              onChange={(e) => setTime(e.target.value)}
              style={{ width: '100%', padding: '8px', marginTop: '5px' }}
            />
          </label>
        </div>
        <button type="submit" style={{ padding: '10px 20px' }}>Check</button>
      </form>
      {error && <p style={{ color: 'red', marginTop: '15px' }}>{error}</p>}
      {result && (
        <div style={{ marginTop: '20px' }}>
          <h3>Result:</h3>
          <p>License Plate: {result.licensePlate}</p>
          <p>Date: {result.date}</p>
          <p>Time: {result.time}</p>
          <p>Allowed to circulate: {result.isAllowed ? 'Yes' : 'No'}</p>
        </div>
      )}
    </div>
  );
}

export default PicoPlacaForm;