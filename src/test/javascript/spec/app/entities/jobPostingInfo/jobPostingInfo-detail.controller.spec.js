'use strict';

describe('JobPostingInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockJobPostingInfo, MockJobType;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockJobPostingInfo = jasmine.createSpy('MockJobPostingInfo');
        MockJobType = jasmine.createSpy('MockJobType');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'JobPostingInfo': MockJobPostingInfo,
            'JobType': MockJobType
        };
        createController = function() {
            $injector.get('$controller')("JobPostingInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:jobPostingInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
