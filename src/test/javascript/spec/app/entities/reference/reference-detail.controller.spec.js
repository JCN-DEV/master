'use strict';

describe('Reference Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockReference, MockEmployee, MockUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockReference = jasmine.createSpy('MockReference');
        MockEmployee = jasmine.createSpy('MockEmployee');
        MockUser = jasmine.createSpy('MockUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Reference': MockReference,
            'Employee': MockEmployee,
            'User': MockUser
        };
        createController = function() {
            $injector.get('$controller')("ReferenceDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:referenceUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
