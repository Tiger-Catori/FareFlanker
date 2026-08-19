import "../../../css/FormButton.css";

const FormButton = ({ href, children, type = 'submit', disabled, className = '', ...rest }) => {
  // If href is provided, render as a link
  if (href) {
    return (
      <div className="btn__link">
        <a href={href} className={className} {...rest}>
          {children}
        </a>
      </div>
    );
  }

  // Otherwise render as a button
  return (
    <button
      type={type}
      disabled={disabled}
      className={`btn__link ${className}`}
      {...rest}
    >
      {children}
    </button>
  );
};

export default FormButton;
