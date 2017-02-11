'use strict';

describe('Job Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockJob, MockCat, MockEmployer, MockCountry, MockUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockJob = jasmine.createSpy('MockJob');
        MockCat = jasmine.createSpy('MockCat');
        MockEmployer = jasmine.createSpy('MockEmployer');
        MockCountry = jasmine.createSpy('MockCountry');
        MockUser = jasmine.createSpy('MockUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Job': MockJob,
            'Cat': MockCat,
            'Employer': MockEmployer,
            'Country': MockCountry,
            'User': MockUser
        };
        createController = function() {
            $injector.get('$controller')("JobDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:jobUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
