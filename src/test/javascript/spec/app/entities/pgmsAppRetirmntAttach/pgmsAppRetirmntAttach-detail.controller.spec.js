'use strict';

describe('PgmsAppRetirmntAttach Detail Controller', function () {
    var $scope, $rootScope;
    var MockEntity, MockPgmsAppRetirmntAttach, MockPgmsRetirmntAttachInfo;
    var createController;

    beforeEach(inject(function ($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPgmsAppRetirmntAttach = jasmine.createSpy('MockPgmsAppRetirmntAttach');
        MockPgmsRetirmntAttachInfo = jasmine.createSpy('MockPgmsRetirmntAttachInfo');


        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity,
            'PgmsAppRetirmntAttach': MockPgmsAppRetirmntAttach,
            'PgmsRetirmntAttachInfo': MockPgmsRetirmntAttachInfo
        };
        createController = function () {
            $injector.get('$controller')("PgmsAppRetirmntAttachDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function () {
        it('Unregisters root scope listener upon scope destruction', function () {
            var eventType = 'stepApp:pgmsAppRetirmntAttachUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
