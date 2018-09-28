import { SbatchModule } from './sbatch.module';

describe('SbatchModule', () => {
  let sbatchModule: SbatchModule;

  beforeEach(() => {
    sbatchModule = new SbatchModule();
  });

  it('should create an instance', () => {
    expect(sbatchModule).toBeTruthy();
  });
});
