'use strict';

describe('RisNewVacancy Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockRisNewVacancy, MockHrDepartmentSetup;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockRisNewVacancy = jasmine.createSpy('MockRisNewVacancy');
        MockHrDepartmentSetup = jasmine.createSpy('MockHrDepartmentSetup');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'RisNewVacancy': MockRisNewVacancy,
            'HrDepartmentSetup': MockHrDepartmentSetup
        };
        createController = function() {
            $injector.get('$controller')("RisNewVacancyDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:risNewVacancyUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
