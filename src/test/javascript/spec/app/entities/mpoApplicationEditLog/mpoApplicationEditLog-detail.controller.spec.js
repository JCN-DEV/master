'use strict';

describe('MpoApplicationEditLog Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockMpoApplicationEditLog, MockMpoApplication;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockMpoApplicationEditLog = jasmine.createSpy('MockMpoApplicationEditLog');
        MockMpoApplication = jasmine.createSpy('MockMpoApplication');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'MpoApplicationEditLog': MockMpoApplicationEditLog,
            'MpoApplication': MockMpoApplication
        };
        createController = function() {
            $injector.get('$controller')("MpoApplicationEditLogDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:mpoApplicationEditLogUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
