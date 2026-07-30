import "../../../css/Button.css"

const AppButton = ({ href, children, ...rest }) => {
  return (
    <>
      <div className="hero__btn">
        <a className="hero__btn-link" href={href} {...rest}>
          {children}
        </a>
      </div>
    </>
  );
};
export default AppButton;
