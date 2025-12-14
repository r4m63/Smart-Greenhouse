import axios, { AxiosError } from 'axios';
import type {
  ModuleSummary,
  ModuleDetails,
  ModuleRegistrationRequest,
  Binding,
  BindRequest,
  Measurement,
  WriteRequest,
  Driver,
  Port,
  ErrorResponse
} from '../types/api';

const api = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json'
  }
});

api.interceptors.response.use(
  (response) => response,
  (error: AxiosError<ErrorResponse>) => {
    if (error.response?.data?.message) {
      error.message = error.response.data.message;
    }
    return Promise.reject(error);
  }
);

export class ApiError extends Error {
  constructor(
    message: string,
    public status?: number,
    public response?: ErrorResponse
  ) {
    super(message);
    this.name = 'ApiError';
  }
}

export const moduleApi = {
  list: async (): Promise<ModuleSummary[]> => {
    const response = await api.get<ModuleSummary[]>('/modules');
    return response.data;
  },

  register: async (request: ModuleRegistrationRequest): Promise<ModuleDetails> => {
    const response = await api.post<ModuleDetails>('/modules', request);
    return response.data;
  },

  getDetails: async (moduleId: number): Promise<ModuleDetails> => {
    const response = await api.get<ModuleDetails>(`/modules/${moduleId}`);
    return response.data;
  },

  sync: async (moduleId: number): Promise<ModuleDetails> => {
    const response = await api.post<ModuleDetails>(`/modules/${moduleId}/sync`);
    return response.data;
  },

  getDrivers: async (moduleId: number): Promise<Driver[]> => {
    const details = await moduleApi.getDetails(moduleId);
    return details.drivers || [];
  },

  getPorts: async (moduleId: number): Promise<Port[]> => {
    const details = await moduleApi.getDetails(moduleId);
    return details.ports || [];
  },

  bind: async (moduleId: number, portId: number, request: BindRequest): Promise<Binding> => {
    const response = await api.put<Binding>(`/modules/${moduleId}/ports/${portId}/bind`, request);
    return response.data;
  },

  getBinding: async (moduleId: number, portId: number): Promise<Binding | null> => {
    try {
      const response = await api.get<Binding>(`/modules/${moduleId}/ports/${portId}/bind`);
      return response.data;
    } catch (error) {
      if (axios.isAxiosError(error) && error.response?.status === 404) {
        return null;
      }
      throw error;
    }
  },

  read: async (moduleId: number, portId: number): Promise<Measurement> => {
    const response = await api.get<Measurement>(`/modules/${moduleId}/ports/${portId}/read`);
    return response.data;
  },

  write: async (moduleId: number, portId: number, request: WriteRequest): Promise<void> => {
    await api.post(`/modules/${moduleId}/ports/${portId}/write`, request);
  }
};

