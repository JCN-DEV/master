'use strict';

describe('PgmsAppRetirmntPen Detail Controller', function () {
    var $scope, $rootScope;
    var MockEntity, MockPgmsAppRetirmntPen, MockHrEmployeeInfo;
    var createController;

    beforeEach(inject(function ($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPgmsAppRetirmntPen = jasmine.createSpy('MockPgmsAppRetirmntPen');
        MockHrEmployeeInfo = jasmine.createSpy('MockHrEmployeeInfo');


        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity,
            'PgmsAppRetirmntPen': MockPgmsAppRetirmntPen,
            'HrEmployeeInfo': MockHrEmployeeInfo
        };
        createController = function () {
            $injector.get('$controller')("PgmsAppRetirmntPenDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function () {
        it('Unregisters root scope listener upon scope destruction', function () {
            var eventType = 'stepApp:pgmsAppRetirmntPenUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
