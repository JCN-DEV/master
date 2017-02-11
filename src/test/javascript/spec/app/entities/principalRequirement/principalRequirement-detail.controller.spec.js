'use strict';

describe('PrincipalRequirement Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockPrincipalRequirement, MockEmployee;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPrincipalRequirement = jasmine.createSpy('MockPrincipalRequirement');
        MockEmployee = jasmine.createSpy('MockEmployee');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'PrincipalRequirement': MockPrincipalRequirement,
            'Employee': MockEmployee
        };
        createController = function() {
            $injector.get('$controller')("PrincipalRequirementDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:principalRequirementUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
