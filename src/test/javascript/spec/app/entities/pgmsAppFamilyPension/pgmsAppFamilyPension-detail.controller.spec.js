'use strict';

describe('PgmsAppFamilyPension Detail Controller', function () {
    var $scope, $rootScope;
    var MockEntity, MockPgmsAppFamilyPension, MockHrEmployeeInfo;
    var createController;

    beforeEach(inject(function ($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPgmsAppFamilyPension = jasmine.createSpy('MockPgmsAppFamilyPension');
        MockHrEmployeeInfo = jasmine.createSpy('MockHrEmployeeInfo');


        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity,
            'PgmsAppFamilyPension': MockPgmsAppFamilyPension,
            'HrEmployeeInfo': MockHrEmployeeInfo
        };
        createController = function () {
            $injector.get('$controller')("PgmsAppFamilyPensionDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function () {
        it('Unregisters root scope listener upon scope destruction', function () {
            var eventType = 'stepApp:pgmsAppFamilyPensionUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
