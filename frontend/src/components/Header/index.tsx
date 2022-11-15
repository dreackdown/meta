import logo from '../../assets/img/logo.svg'
import './styles.css'

function Header() {
  return (
    <header className="dsmeta-logo-container">
      <div>
        <img src={logo} alt="DSMeta" />
        <h1>DSMeta</h1>
        <p>
          Desenvolvido por
          <a href="https://www.instagram.com/devsuperior.ig">@devsuperior.ig</a>
        </p>
      </div>
    </header>
  )
}

export default Header
