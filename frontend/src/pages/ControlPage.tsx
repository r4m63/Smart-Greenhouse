import { useState, useEffect } from 'react';
import { moduleApi } from '../services/api';
import type { ModuleSummary, AutomationRule } from '../types/api';
import AutomationRuleForm from '../components/AutomationRuleForm';
import AutomationRuleList from '../components/AutomationRuleList';
import './ControlPage.css';

let automationRules: AutomationRule[] = [];
let nextRuleId = 1;

function ControlPage() {
  const [modules, setModules] = useState<ModuleSummary[]>([]);
  const [rules, setRules] = useState<AutomationRule[]>(automationRules);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [showAddRule, setShowAddRule] = useState(false);

  useEffect(() => {
    loadModules();
  }, []);

  const loadModules = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await moduleApi.list();
      setModules(data);
    } catch (err) {
      setError('Не удалось загрузить список модулей');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleAddRule = (rule: Omit<AutomationRule, 'id'>) => {
    const newRule: AutomationRule = {
      ...rule,
      id: nextRuleId++
    };
    automationRules = [...automationRules, newRule];
    setRules([...automationRules]);
    setShowAddRule(false);
  };

  const handleDeleteRule = (ruleId: number) => {
    automationRules = automationRules.filter(r => r.id !== ruleId);
    setRules([...automationRules]);
  };

  const handleToggleRule = (ruleId: number) => {
    automationRules = automationRules.map(r =>
      r.id === ruleId ? { ...r, enabled: !r.enabled } : r
    );
    setRules([...automationRules]);
  };

  if (loading) {
    return <div className="loading">Загрузка...</div>;
  }

  return (
    <div className="control-page">
      <div className="page-header">
        <h2>Управление</h2>
        <button className="button" onClick={() => setShowAddRule(true)}>
          + Добавить правило
        </button>
      </div>

      {error && <div className="error">{error}</div>}

      <div className="control-content">
        <div className="card">
          <h3 className="card-title">Правила автоматизации</h3>
          <p className="card-description">
            Настройте автоматические действия на основе показаний датчиков.
            Например: если температура выше 25°C, включить вентилятор на 50%.
          </p>

          <AutomationRuleList
            rules={rules}
            modules={modules}
            onDelete={handleDeleteRule}
            onToggle={handleToggleRule}
          />
        </div>
      </div>

      {showAddRule && (
        <AutomationRuleForm
          modules={modules}
          onClose={() => setShowAddRule(false)}
          onSave={handleAddRule}
        />
      )}
    </div>
  );
}

export default ControlPage;

