'use strict';

describe('JpEmployeeExperience Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockJpEmployeeExperience, MockJpEmployee;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockJpEmployeeExperience = jasmine.createSpy('MockJpEmployeeExperience');
        MockJpEmployee = jasmine.createSpy('MockJpEmployee');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'JpEmployeeExperience': MockJpEmployeeExperience,
            'JpEmployee': MockJpEmployee
        };
        createController = function() {
            $injector.get('$controller')("JpEmployeeExperienceDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:jpEmployeeExperienceUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
