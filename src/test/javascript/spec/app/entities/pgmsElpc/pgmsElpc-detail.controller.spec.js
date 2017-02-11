'use strict';

describe('PgmsElpc Detail Controller', function () {
    var $scope, $rootScope;
    var MockEntity, MockPgmsElpc, MockHrEmployeeInfo;
    var createController;

    beforeEach(inject(function ($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPgmsElpc = jasmine.createSpy('MockPgmsElpc');
        MockHrEmployeeInfo = jasmine.createSpy('MockHrEmployeeInfo');


        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity,
            'PgmsElpc': MockPgmsElpc,
            'HrEmployeeInfo': MockHrEmployeeInfo
        };
        createController = function () {
            $injector.get('$controller')("PgmsElpcDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function () {
        it('Unregisters root scope listener upon scope destruction', function () {
            var eventType = 'stepApp:pgmsElpcUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
