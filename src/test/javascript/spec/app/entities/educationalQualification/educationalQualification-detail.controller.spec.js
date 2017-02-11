'use strict';

describe('EducationalQualification Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockEducationalQualification, MockEmployee, MockUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockEducationalQualification = jasmine.createSpy('MockEducationalQualification');
        MockEmployee = jasmine.createSpy('MockEmployee');
        MockUser = jasmine.createSpy('MockUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'EducationalQualification': MockEducationalQualification,
            'Employee': MockEmployee,
            'User': MockUser
        };
        createController = function() {
            $injector.get('$controller')("EducationalQualificationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:educationalQualificationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
