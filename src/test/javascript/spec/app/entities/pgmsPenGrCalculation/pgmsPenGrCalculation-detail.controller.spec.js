'use strict';

describe('PgmsPenGrCalculation Detail Controller', function () {
    var $scope, $rootScope;
    var MockEntity, MockPgmsPenGrCalculation, MockHrGradeSetup, MockHrPayScaleSetup, MockPgmsPenGrRate;
    var createController;

    beforeEach(inject(function ($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPgmsPenGrCalculation = jasmine.createSpy('MockPgmsPenGrCalculation');
        MockHrGradeSetup = jasmine.createSpy('MockHrGradeSetup');
        MockHrPayScaleSetup = jasmine.createSpy('MockHrPayScaleSetup');
        MockPgmsPenGrRate = jasmine.createSpy('MockPgmsPenGrRate');


        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity,
            'PgmsPenGrCalculation': MockPgmsPenGrCalculation,
            'HrGradeSetup': MockHrGradeSetup,
            'HrPayScaleSetup': MockHrPayScaleSetup,
            'PgmsPenGrRate': MockPgmsPenGrRate
        };
        createController = function () {
            $injector.get('$controller')("PgmsPenGrCalculationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function () {
        it('Unregisters root scope listener upon scope destruction', function () {
            var eventType = 'stepApp:pgmsPenGrCalculationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
