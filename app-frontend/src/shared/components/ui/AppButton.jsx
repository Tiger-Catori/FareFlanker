import "../../../css/Button.css";

const AppButton = ({ href, children, className = "", ...rest }) => {
  return (
    <a
      href={href}
      className={`btn btn--primary ${className}`}
      {...rest}
    >
      {children}
    </a>
  );
};

export default AppButton;
