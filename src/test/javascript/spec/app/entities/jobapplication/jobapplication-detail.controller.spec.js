'use strict';

describe('Jobapplication Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockJobapplication, MockUser, MockJob;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockJobapplication = jasmine.createSpy('MockJobapplication');
        MockUser = jasmine.createSpy('MockUser');
        MockJob = jasmine.createSpy('MockJob');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Jobapplication': MockJobapplication,
            'User': MockUser,
            'Job': MockJob
        };
        createController = function() {
            $injector.get('$controller')("JobapplicationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:jobapplicationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
