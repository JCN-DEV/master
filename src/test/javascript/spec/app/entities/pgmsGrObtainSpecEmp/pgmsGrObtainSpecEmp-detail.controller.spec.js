'use strict';

describe('PgmsGrObtainSpecEmp Detail Controller', function () {
    var $scope, $rootScope;
    var MockEntity, MockPgmsGrObtainSpecEmp, MockHrEmployeeInfo;
    var createController;

    beforeEach(inject(function ($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPgmsGrObtainSpecEmp = jasmine.createSpy('MockPgmsGrObtainSpecEmp');
        MockHrEmployeeInfo = jasmine.createSpy('MockHrEmployeeInfo');


        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity,
            'PgmsGrObtainSpecEmp': MockPgmsGrObtainSpecEmp,
            'HrEmployeeInfo': MockHrEmployeeInfo
        };
        createController = function () {
            $injector.get('$controller')("PgmsGrObtainSpecEmpDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function () {
        it('Unregisters root scope listener upon scope destruction', function () {
            var eventType = 'stepApp:pgmsGrObtainSpecEmpUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
