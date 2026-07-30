import FormButton from "./FormButton";

const AppCard = () => {
  return (
    <div className="card__container">
      <div className="card">
        <div className="search__box">
          <div className="heading__box">
            <h2>Search for Flights</h2>
          </div>
          <FormInputs />
          <FormButton href=''>Search Flights</FormButton >
        </div>
      </div>
    </div>
  )
}

export default AppCard;

const FormInputs = () => {
  return (
    <>
      <div className="airport-search__box">
        <div className="input__box">
          <label htmlFor='from'>From</label>
          <input
            type="text"
            className="airport__input"
            id='from'
            name="originEntry"
            placeholder="Enter origin"
            required
            />
        </div>

        <div className="input__box">
          <label htmlFor='to'>To</label>
          <input
            type="text"
            className="airport__input"
            id='to'
            name="destinationEntry"
            placeholder="Enter destination"
            required
          />
        </div>

        <div className="date-dropdown__row">
          <div className="date__box">
            <label htmlFor="date">Date</label>
            <input
              type="date"
              className="date__input"
              id="date"
              name="date__entry"
              required
            />
          </div>

          <div className="dropdown">
            <label htmlFor="passengers">Passengers</label>

            <div className="select__wrapper">
              <select id="passengers">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>

              </select>
              <ion-icon name="chevron-down-outline"></ion-icon>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}
