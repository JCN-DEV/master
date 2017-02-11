'use strict';

describe('BEdApplicationEditLog Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockBEdApplicationEditLog, MockBEdApplication;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockBEdApplicationEditLog = jasmine.createSpy('MockBEdApplicationEditLog');
        MockBEdApplication = jasmine.createSpy('MockBEdApplication');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'BEdApplicationEditLog': MockBEdApplicationEditLog,
            'BEdApplication': MockBEdApplication
        };
        createController = function() {
            $injector.get('$controller')("BEdApplicationEditLogDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:bEdApplicationEditLogUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
