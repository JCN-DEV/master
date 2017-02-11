'use strict';

describe('InstEmplExperience Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstEmplExperience, MockInstEmployee;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstEmplExperience = jasmine.createSpy('MockInstEmplExperience');
        MockInstEmployee = jasmine.createSpy('MockInstEmployee');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstEmplExperience': MockInstEmplExperience,
            'InstEmployee': MockInstEmployee
        };
        createController = function() {
            $injector.get('$controller')("InstEmplExperienceDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instEmplExperienceUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
