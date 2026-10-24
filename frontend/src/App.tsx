
import { useState, type FormEvent } from 'react'
import './App.css'

type BankResult = {
  bankName: string
  status: 'APPROVED' | 'REJECTED' | 'UNAVAILABLE'
  monthlyInterestRate: number | null
  installmentAmount: number | null
  totalAmount: number | null
}

function App() {
  const [results, setResults] = useState<BankResult[]>([])
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)
  const [searched, setSearched] = useState(false)

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()

    const form = new FormData(event.currentTarget)

    const data = {
      name: form.get('name'),
      email: form.get('email'),
      cpf: form.get('cpf'),
      employerCnpj: form.get('employerCnpj'),
      salary: Number(form.get('salary')),
      loanAmount: Number(form.get('loanAmount')),
      installments: Number(form.get('installments'))
    }

    setLoading(true)
    setError('')
    setResults([])
    setSearched(false)

    try {
      const response = await fetch('/api/simulations', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
      })

      if (!response.ok) {
        throw new Error(
            'Não foi possível realizar a simulação. Verifique os dados informados.'
        )
      }

      const result: BankResult[] = await response.json()

      setResults(result)
      setSearched(true)

    } catch (error) {
      if (error instanceof Error) {
        setError(error.message)
      } else {
        setError('Erro ao realizar a simulação.')
      }
    } finally {
      setLoading(false)
    }
  }

  function formatMoney(value: number | null) {
    if (value === null) {
      return 'Não informado'
    }

    return value.toLocaleString('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    })
  }

  return (
      <main className="container">
        <h1>Simulação de empréstimo</h1>
        <p>Informe os dados para consultar as propostas disponíveis.</p>

        <form className="form" onSubmit={handleSubmit}>

          <div className="field">
            <label htmlFor="name">Nome completo</label>
            <input
                id="name"
                name="name"
                type="text"
                required
            />
          </div>

          <div className="field">
            <label htmlFor="email">E-mail</label>
            <input
                id="email"
                name="email"
                type="email"
                required
            />
          </div>

          <div className="field">
            <label htmlFor="cpf">CPF</label>
            <input
                id="cpf"
                name="cpf"
                type="text"
                required
            />
          </div>

          <div className="field">
            <label htmlFor="employerCnpj">CNPJ</label>
            <input
                id="employerCnpj"
                name="employerCnpj"
                type="text"
                required
            />
          </div>

          <div className="field">
            <label htmlFor="salary">Salário mensal (R$)</label>
            <input
                id="salary"
                name="salary"
                type="number"
                min="0.01"
                step="0.01"
                required
            />
          </div>

          <div className="field">
            <label htmlFor="loanAmount">Valor solicitado (R$)</label>
            <input
                id="loanAmount"
                name="loanAmount"
                type="number"
                min="0.01"
                step="0.01"
                required
            />
          </div>

          <div className="field">
            <label htmlFor="installments">Quantidade de parcelas</label>
            <input
                id="installments"
                name="installments"
                type="number"
                min="1"
                step="1"
                required
            />
          </div>

          <button type="submit" disabled={loading}>
            {loading && 'Simulando...'}
            {!loading && 'Simular'}
          </button>
        </form>

        {error && (
            <p className="error">{error}</p>
        )}

        {searched && (
            <section className="results">
              <h2>Resultados</h2>

              {results.length === 0 && (
                  <p>Nenhum resultado disponível.</p>
              )}

              {results.map(bank => (
                  <div className="bank" key={bank.bankName}>
                    <h3>{bank.bankName}</h3>

                    {bank.status === 'APPROVED' && (
                        <>
                          <p>Aprovado</p>
                          <p>Juros mensais: {bank.monthlyInterestRate}%</p>
                          <p>Parcela: {formatMoney(bank.installmentAmount)}</p>
                          <p>Total: {formatMoney(bank.totalAmount)}</p>
                        </>
                    )}

                    {bank.status === 'REJECTED' && (
                        <p>Não aprovado.</p>
                    )}

                    {bank.status === 'UNAVAILABLE' && (
                        <p>Banco indisponível no momento.</p>
                    )}
                  </div>
              ))}
            </section>
        )}
      </main>
  )
}

export default App
