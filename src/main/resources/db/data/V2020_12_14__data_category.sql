INSERT INTO category_table
    (category_name,category_display_name,category_id)
VALUES
    ('nature','Nature','nature'),
    ('edu','Education','Education_id'),
    ('IT','Information Technology','IT_id'),
    ('healthCare','HealthCare','HealthCare_id'),
    ('children','Children','children');

INSERT INTO content_table
    (content_id, content_display_name, content_description, content_thumbnail, duration, pricing, category_id)
VALUES
    ('nature-ocean', 'Learn Web development from scratch using HTML, CSS & JS', 'Begin learning the in demand skill of programming using HTML 5 and CSS 3. Don''t wait, start learning HTML and CSS today!', 'https://videolib-upload-folder.s3.ap-south-1.amazonaws.com/oceans-small.jpg', '12 hours', '420', 'nature'),
    ('nature-forest', 'Learn mobile app development using Flutter', 'Flutter app development is one of the fastest growing market trend. Make sure to ride it.', 'https://videolib-upload-folder.s3.ap-south-1.amazonaws.com/oceans-small.jpg', '9 hours', '420', 'nature'),
    ('nature-glacier', 'Glacier', 'Contains videos showing freezing glaciers', 'https://videolib-upload-folder.s3.ap-south-1.amazonaws.com/oceans-small.jpg', '12 hours', '420', 'nature'),
    ('children-rhymes', 'Rhymes', 'Contains videos showing beautiful waves of the ocean', 'https://videolib-upload-folder.s3.ap-south-1.amazonaws.com/children-small.jpg', '12 hours', '420', 'children'),
    ('children-cartoon', 'Cartoon', 'Cool cartoon Videos', 'https://videolib-upload-folder.s3.ap-south-1.amazonaws.com/children-small.jpg', '12 hours', '420', 'children'),
    ('children-alphabets', 'Alphabets', 'Contains videos teaching children about alphabets', 'https://videolib-upload-folder.s3.ap-south-1.amazonaws.com/children-small.jpg', '12 hours', '420', 'children'),
    ('children-good-bad-touch', 'Good touch - Bad touch', 'Contains video explaining good touch and bad touch', 'https://videolib-upload-folder.s3.ap-south-1.amazonaws.com/children-small.jpg', '12 hours', '420', 'children');